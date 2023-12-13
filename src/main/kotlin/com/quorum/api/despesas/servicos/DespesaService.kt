package com.quorum.api.despesas.servicos

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.despesas.modelos.Despesa
import com.quorum.api.despesas.repositories.RepositorioDespesa
import com.quorum.api.redisflag.ChaveAtualizacao
import com.quorum.api.redisflag.RedisCacheFlag
import com.quorum.api.redisflag.RepositorioRedisCacheFlag
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
    fun atualizarDespesas(ano: String, mes: String): List<Despesa> {
        val url = obterDebitoVereador
        val xmlResponse = makePostRequest(url, ano, mes)
        val responseObj = parseXmlResponse(xmlResponse)

        val despesasDistintas = responseObj.items.distinctBy { it.nomeDespesa }
        val despesas = despesasDistintas.map {
            repositorioDespesa.findByNomeCategoria(it.nomeDespesa)
                ?: repositorioDespesa.save(Despesa(nomeCategoria = it.nomeDespesa))
        }

        repositorioRedisCacheFlag.save(
            RedisCacheFlag(
                id = ChaveAtualizacao.ULTIMA_ATUALIZACAO_DESPESAS.name,
                valor = ZonedDateTime.now()
            )
        )

        return despesas
    }

    @Transactional
    fun criarDespesa(despesa: Despesa): Despesa {
        return repositorioDespesa.save(despesa)
    }
}
