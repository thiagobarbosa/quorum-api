package com.quorum.api.fornecedores.servicos

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.fornecedores.modelos.Fornecedor
import com.quorum.api.fornecedores.repositories.RepositorioFornecedor
import com.quorum.api.redisflag.ChaveAtualizacao
import com.quorum.api.redisflag.RedisCacheFlag
import com.quorum.api.redisflag.RepositorioRedisCacheFlag
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parseXmlResponse
import java.time.ZonedDateTime

@Service
class ServicoFornecedor(
    private val repositorioFornecedor: RepositorioFornecedor,
    private val repositorioRedisCacheFlag: RepositorioRedisCacheFlag
) {

    @Transactional
    fun apagarTodosFornecedores(): List<Fornecedor> {
        val fornecedores = repositorioFornecedor.findAll().toList()
        repositorioFornecedor.deleteAll(fornecedores)
        return fornecedores
    }

    fun obterTodosFornecedores(): List<Fornecedor> {
        return repositorioFornecedor.findAll().toList()
    }

    fun obterFornecedorPorCnpj(id: String): Fornecedor? {
        return repositorioFornecedor.findById(id).orElse(null)
    }

    @Transactional
    fun atualizarFornecedores(ano: String, mes: String): List<Fornecedor> {
        val url = obterDebitoVereador
        val xmlResponse = makePostRequest(url, ano, mes)
        val responseObj = parseXmlResponse(xmlResponse)

        val fornecedoresDistintos = responseObj.items.distinctBy { it.fornecedor }
        val fornecedores = fornecedoresDistintos.map {
            val cnpjFormatado = it.cnpj.replace(".", "").replace("/", "").replace("-", "")
            repositorioFornecedor.findByCnpj(cnpjFormatado)
                ?: repositorioFornecedor.save(
                    Fornecedor(
                        cnpj = cnpjFormatado,
                        nome = it.fornecedor
                    )
                )
        }

        repositorioRedisCacheFlag.save(
            RedisCacheFlag(
                id = ChaveAtualizacao.ULTIMA_ATUALIZACAO_FORNECEDORES.name,
                valor = ZonedDateTime.now()
            )
        )

        return fornecedores
    }

    @Transactional
    fun criarFornecedor(fornecedor: Fornecedor): Fornecedor {
        return repositorioFornecedor.save(fornecedor)
    }
}
