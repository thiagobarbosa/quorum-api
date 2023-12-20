package com.quorum.api.fornecedores.servicos

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.fornecedores.modelos.Fornecedor
import com.quorum.api.fornecedores.repositories.RepositorioFornecedor
import com.quorum.api.utils.ANO_ATUAL
import com.quorum.api.utils.ANO_INICIO
import com.quorum.api.utils.MES_ATUAL
import com.quorum.api.utils.defaultPageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parseXmlResponse

@Service
class ServicoFornecedor(
    private val repositorioFornecedor: RepositorioFornecedor
) {

    @Transactional
    fun apagarTodosFornecedores(): List<Fornecedor> {
        val fornecedores = repositorioFornecedor.findAll().toList()
        repositorioFornecedor.deleteAll(fornecedores)
        return fornecedores
    }

    fun obterTodosFornecedores(page: Int? = 0, pageSize: Int? = 100): List<Fornecedor> {
        return repositorioFornecedor.findAll(defaultPageable(page, pageSize)).toList()
    }

    fun obterFornecedorPorCnpj(id: String): Fornecedor? {
        return repositorioFornecedor.findById(id).orElse(null)
    }

    @Transactional
    fun atualizarFornecedores(ano: Int): List<Fornecedor> {
        if (ano > ANO_ATUAL || ano < ANO_INICIO) {
            throw Exception("Dados disponiveis somente a partir de $ANO_INICIO até $ANO_ATUAL")
        }

        val ultimoMes = if (ano == ANO_ATUAL) MES_ATUAL.minus(1) else 12
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

        return fornecedoresAdicionados
    }

    @Transactional
    fun criarFornecedor(fornecedor: Fornecedor): Fornecedor {
        return repositorioFornecedor.save(fornecedor)
    }
}
