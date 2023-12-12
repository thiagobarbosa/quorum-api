package com.quorum.api.models

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

data class ItemsReembolso(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TabelaPortalITEMREEMBOLSO")
    val items: List<ItemReembolso>
)

data class ItemReembolso(
    @JacksonXmlProperty(localName = "Chave")
    val chave: String = "",
    @JacksonXmlProperty(localName = "CENTROCUSTOSID")
    val centroCustosId: Double = 0.0,
    @JacksonXmlProperty(localName = "DEPARTAMENTO")
    val departamento: String = "",
    @JacksonXmlProperty(localName = "TIPODEPARTAMENTO")
    val tipoDepartamento: Int = 0,
    @JacksonXmlProperty(localName = "VEREADOR")
    val vereador: String = "",
    @JacksonXmlProperty(localName = "ANO")
    val ano: Int = 0,
    @JacksonXmlProperty(localName = "MES")
    val mes: Int = 0,
    @JacksonXmlProperty(localName = "DESPESA")
    val despesa: String = "",
    @JacksonXmlProperty(localName = "CNPJ")
    val cnpj: String = "",
    @JacksonXmlProperty(localName = "FORNECEDOR")
    val fornecedor: String = "",
    @JacksonXmlProperty(localName = "VALOR")
    val valor: Double = 0.0
)
