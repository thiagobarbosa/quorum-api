package com.quorum.api.fornecedores.servicos

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.fornecedores.modelos.Fornecedor
import com.quorum.api.fornecedores.repositories.RepositorioFornecedor
import com.quorum.api.redisflag.ChaveAtualizacao
import com.quorum.api.redisflag.RedisCacheFlag
import com.quorum.api.redisflag.RepositorioRedisCacheFlag
import com.quorum.api.utils.ANO_ATUAL
import com.quorum.api.utils.ANO_INICIO
import com.quorum.api.utils.MES_ATUAL
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
    fun atualizarFornecedores(ano: Int): List<Fornecedor> {
        if (ano > ANO_ATUAL || ano < ANO_INICIO) {
            throw Exception("Dados disponiveis somente a partir de $ANO_INICIO até $ANO_ATUAL")
        }

        val ultimoMes = if (ano == ANO_ATUAL) MES_ATUAL else 12
        val fornecedoresAdicionados: MutableList<Fornecedor> = mutableListOf()

        val url = obterDebitoVereador

        (1..ultimoMes).forEach { mes ->
            val xmlResponse = makePostRequest(url, ano, mes)
            val responseObj = parseXmlResponse(xmlResponse)

            val fornecedoresDistintos = responseObj.items.distinctBy { it.fornecedor }
            fornecedoresAdicionados.addAll(
                fornecedoresDistintos.map {
                    val cnpjFormatado = it.cnpj.replace(".", "").replace("/", "").replace("-", "")
                    repositorioFornecedor.findByCnpj(cnpjFormatado)
                        ?: repositorioFornecedor.save(
                            Fornecedor(
                                cnpj = cnpjFormatado,
                                nome = it.fornecedor
                            )
                        )
                }
            )
        }

        repositorioRedisCacheFlag.save(
            RedisCacheFlag(
                id = ChaveAtualizacao.ULTIMA_ATUALIZACAO_FORNECEDORES.name,
                valor = ZonedDateTime.now()
            )
        )

        return fornecedoresAdicionados
    }

    @Transactional
    fun criarFornecedor(fornecedor: Fornecedor): Fornecedor {
        return repositorioFornecedor.save(fornecedor)
    }
}
