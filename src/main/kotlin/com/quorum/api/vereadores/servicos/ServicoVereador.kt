package com.quorum.api.vereadores.servicos

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.redisflag.ChaveAtualizacao
import com.quorum.api.redisflag.RedisCacheFlag
import com.quorum.api.redisflag.RepositorioRedisCacheFlag
import com.quorum.api.utils.ANO_ATUAL
import com.quorum.api.utils.ANO_INICIO
import com.quorum.api.utils.MES_ATUAL
import com.quorum.api.vereadores.modelos.Vereador
import com.quorum.api.vereadores.repositorios.RepositorioVereador
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parseXmlResponse
import java.time.ZonedDateTime

@Service
class ServicoVereador(
    private val repositorioVereador: RepositorioVereador,
    private val repositorioRedisCacheFlag: RepositorioRedisCacheFlag
) {

    @Transactional
    fun apagarTodosVereadores(): List<Vereador> {
        val vereadores = repositorioVereador.findAll().toList()
        repositorioVereador.deleteAll(vereadores)
        return vereadores
    }

    fun obterVereadorPorId(id: String): Vereador? {
        return repositorioVereador.findById(id).orElse(null)
    }

    fun obterVereadorPorNome(nome: String): Vereador? {
        return repositorioVereador.findByNome(nome)
    }

    fun obterTodosVereadores(): List<Vereador> {
        return repositorioVereador.findAll().toList()
    }

    @Transactional
    fun atualizarVereadores(ano: Int): List<Vereador> {
        if (ano > ANO_ATUAL || ano < ANO_INICIO) {
            throw Exception("Dados disponiveis somente a partir de $ANO_INICIO até $ANO_ATUAL")
        }

        val url = obterDebitoVereador

        val ultimoMes = if (ano == ANO_ATUAL) MES_ATUAL.minus(1) else 12
        val vereadoresAdicionados: MutableList<Vereador> = mutableListOf()

        (1..ultimoMes).forEach { mes ->
            val xmlResponse = makePostRequest(url, ano, mes)
            val responseObj = parseXmlResponse(xmlResponse)

            val vereadoresDistintos = responseObj.items.distinctBy { it.idVereador }
            vereadoresAdicionados.addAll(
                vereadoresDistintos.map {
                    repositorioVereador.findById(it.idVereador).orElse(
                        repositorioVereador.save(
                            Vereador(
                                id = it.idVereador,
                                nome = it.nomeVereador
                            )
                        )
                    )
                }
            )
        }

        repositorioRedisCacheFlag.save(
            RedisCacheFlag(
                id = ChaveAtualizacao.ULTIMA_ATUALIZACAO_VEREADORES.name,
                valor = ZonedDateTime.now()
            )
        )

        return vereadoresAdicionados
    }

    @Transactional
    fun criarVereador(vereador: Vereador): Vereador {
        return repositorioVereador.save(vereador)
    }
}
