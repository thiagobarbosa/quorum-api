package com.quorum.api.admin

import com.quorum.api.despesas.models.Despesa
import com.quorum.api.despesas.services.DespesaService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN")
@RestController
@RequestMapping("/v1/admin/despesas")
class AdminDespesaController(
    private val despesaService: DespesaService
) {

    @PutMapping("/atualizar")
    fun atualizarDespesas(
        @RequestParam ano: String,
        @RequestParam mes: String
    ): List<Despesa> {
        return despesaService.atualizarDespesas(ano, mes)
    }

    @DeleteMapping("/apagar/todas")
    fun apagarTodasDespesas(): List<Despesa> {
        return despesaService.apagarTodasDespesas()
    }
}
