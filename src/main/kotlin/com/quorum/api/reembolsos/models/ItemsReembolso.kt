package com.quorum.api.reembolsos.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.util.*

data class ItemsReembolso(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TabelaPortalITEMREEMBOLSO")
    val items: List<ItemReembolso>
)

@RedisHash("ItemReembolso")
data class ItemReembolso(
    @Id
    val id: String = UUID.randomUUID().toString(),
    @JacksonXmlProperty(localName = "Chave")
    @Indexed
    val idVereador: String,
    @JacksonXmlProperty(localName = "VEREADOR")
    val vereadorName: String,
    @JacksonXmlProperty(localName = "CENTROCUSTOSID")
    val centroCustosId: Double,
    @JacksonXmlProperty(localName = "DEPARTAMENTO")
    val departamento: String,
    @JacksonXmlProperty(localName = "TIPODEPARTAMENTO")
    val tipoDepartamento: Int,
    @JacksonXmlProperty(localName = "ANO")
    val ano: Int,
    @JacksonXmlProperty(localName = "MES")
    val mes: Int,
    @JacksonXmlProperty(localName = "DESPESA")
    val despesaName: String,
    @Indexed
    val despesaId: String? = null,
    @JacksonXmlProperty(localName = "CNPJ")
    @Indexed
    val cnpj: String,
    @JacksonXmlProperty(localName = "FORNECEDOR")
    val fornecedor: String,
    @JacksonXmlProperty(localName = "VALOR")
    val valor: Double
)
