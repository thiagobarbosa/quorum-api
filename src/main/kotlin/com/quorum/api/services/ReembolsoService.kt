package com.quorum.api.services

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.models.Despesa
import com.quorum.api.models.Fornecedor
import com.quorum.api.models.ItemReembolso
import com.quorum.api.models.Vereador
import com.quorum.api.repositories.ReembolsoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parseXmlResponse

@Service
class ReembolsoService(
    private val reembolsoRepository: ReembolsoRepository,
    private val despesaService: DespesaService,
    private val vereadorService: VereadorService,
    private val fornecedorService: FornecedorService
) {

    @Transactional
    fun deleteAllReembolsos() {
        reembolsoRepository.deleteAll()
    }

    @Transactional
    fun updateReembolsos(ano: String, mes: String): List<ItemReembolso> {
        // Since the source API doesn't have a unique identifier for each item, we need to delete all
        reembolsoRepository.deleteAll()
        val url = obterDebitoVereador
        val xmlResponse = makePostRequest(url, ano, mes)
        val responseObj = parseXmlResponse(xmlResponse)

        return responseObj.items.map {
            val formattedCnpj = it.cnpj.replace(".", "").replace("/", "").replace("-", "")
            val despesa = despesaService.getDespesaByName(it.despesaName) ?: despesaService.createDespesa(Despesa(name = it.despesaName))
            val vereador = vereadorService.getVereadorByName(it.vereadorName) ?: vereadorService.createVereador(Vereador(id = it.idVereador, name = it.vereadorName))
            val fornecedor = fornecedorService.getFornecedorByCnpj(formattedCnpj) ?: fornecedorService.createFornecedor(
                Fornecedor(cnpj = formattedCnpj, name = it.fornecedor)
            )
            reembolsoRepository.save(
                ItemReembolso(
                    idVereador = vereador.id,
                    vereadorName = vereador.name,
                    centroCustosId = it.centroCustosId,
                    departamento = it.departamento,
                    tipoDepartamento = it.tipoDepartamento,
                    ano = it.ano,
                    mes = it.mes,
                    despesaName = despesa.name,
                    despesaId = despesa.id,
                    cnpj = fornecedor.cnpj,
                    fornecedor = fornecedor.name,
                    valor = it.valor
                )
            )
        }
    }

    fun getAllReembolsos(): List<ItemReembolso> {
        return reembolsoRepository.findAll().toList()
    }

    fun getReembolsosByIdVereador(id: String): List<ItemReembolso> {
        return reembolsoRepository.findAllByIdVereador(id)
    }

    fun getReembolsosByDespesaId(despesaId: String): List<ItemReembolso> {
        return reembolsoRepository.findAllByDespesaId(despesaId).toList()
    }

    fun getReembolsosByFornecedor(fornecedor: String): List<ItemReembolso> {
        return reembolsoRepository.findAllByCnpj(fornecedor)
    }
}
