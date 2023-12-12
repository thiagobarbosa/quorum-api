package com.quorum.api.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
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
    val idVereador: String,
    @JacksonXmlProperty(localName = "CENTROCUSTOSID")
    val centroCustosId: Double,
    @JacksonXmlProperty(localName = "DEPARTAMENTO")
    val departamento: String,
    @JacksonXmlProperty(localName = "TIPODEPARTAMENTO")
    val tipoDepartamento: Int,
    @JacksonXmlProperty(localName = "VEREADOR")
    val vereador: String,
    @JacksonXmlProperty(localName = "ANO")
    val ano: Int,
    @JacksonXmlProperty(localName = "MES")
    val mes: Int,
    @JacksonXmlProperty(localName = "DESPESA")
    val despesa: String,
    @JacksonXmlProperty(localName = "CNPJ")
    val cnpj: String,
    @JacksonXmlProperty(localName = "FORNECEDOR")
    val fornecedor: String,
    @JacksonXmlProperty(localName = "VALOR")
    val valor: Double
)
