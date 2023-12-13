package com.quorum.api.vereadores.services

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.vereadores.models.Vereador
import com.quorum.api.vereadores.repositorios.RepositorioVereador
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parseXmlResponse

@Service
class ServicoVereador(
    private val repositorioVereador: RepositorioVereador
) {

    @Transactional
    fun apagarTodosVereadores() {
        repositorioVereador.deleteAll()
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
    fun atualizarVereadores(ano: String, mes: String): List<Vereador> {
        val url = obterDebitoVereador
        val xmlResponse = makePostRequest(url, ano, mes)
        val responseObj = parseXmlResponse(xmlResponse)

        val vereadoresDistintos = responseObj.items.distinctBy { it.idVereador }
        return vereadoresDistintos.map {
            repositorioVereador.findById(it.idVereador).orElse(
                repositorioVereador.save(
                    Vereador(
                        id = it.idVereador,
                        nome = it.nomeVereador
                    )
                )
            )
        }
    }

    @Transactional
    fun criarVereador(vereador: Vereador): Vereador {
        return repositorioVereador.save(vereador)
    }
}
