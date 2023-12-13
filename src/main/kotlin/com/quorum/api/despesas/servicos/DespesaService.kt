package com.quorum.api.despesas.servicos

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.despesas.modelos.Despesa
import com.quorum.api.despesas.repositories.RepositorioDespesa
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
class DespesaService(
    private val repositorioDespesa: RepositorioDespesa,
    private val repositorioRedisCacheFlag: RepositorioRedisCacheFlag
) {

    @Transactional
    fun apagarTodasDespesas(): List<Despesa> {
        val despesas = repositorioDespesa.findAll().toList()
        repositorioDespesa.deleteAll(despesas)
        return despesas
    }

    fun obterTodasDespesas(): List<Despesa> {
        return repositorioDespesa.findAll().toList()
    }

    fun obterDespesaPorId(id: String): Despesa? {
        return repositorioDespesa.findById(id).orElse(null)
    }

    fun obterDespesaPorNome(nome: String): Despesa? {
        return repositorioDespesa.findByNomeCategoria(nome)
    }

    @Transactional
    fun atualizarDespesas(ano: Int): List<Despesa> {
        if (ano > ANO_ATUAL || ano < ANO_INICIO) {
            throw Exception("Dados disponiveis somente a partir de $ANO_INICIO até $ANO_ATUAL")
        }

        val url = obterDebitoVereador

        val ultimoMes = if (ano == ANO_ATUAL) MES_ATUAL else 12
        val despesasAdicionadas: MutableList<Despesa> = mutableListOf()

        (1..ultimoMes).forEach { mes ->
            val xmlResponse = makePostRequest(url, ano, mes)
            val responseObj = parseXmlResponse(xmlResponse)

            val despesasDistintas = responseObj.items.distinctBy { it.nomeDespesa }
            despesasAdicionadas.addAll(
                despesasDistintas.map {
                    repositorioDespesa.findByNomeCategoria(it.nomeDespesa)
                        ?: repositorioDespesa.save(Despesa(nomeCategoria = it.nomeDespesa))
                }
            )
        }

        repositorioRedisCacheFlag.save(
            RedisCacheFlag(
                id = ChaveAtualizacao.ULTIMA_ATUALIZACAO_DESPESAS.name,
                valor = ZonedDateTime.now()
            )
        )

        return despesasAdicionadas
    }

    @Transactional
    fun criarDespesa(despesa: Despesa): Despesa {
        return repositorioDespesa.save(despesa)
    }
}
