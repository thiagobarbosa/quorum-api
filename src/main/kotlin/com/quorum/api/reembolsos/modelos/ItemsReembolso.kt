package com.quorum.api.reembolsos.modelos

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.quorum.api.annotations.NoArgConstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.Id
import javax.persistence.Table

data class ItemsReembolso(
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "TabelaPortalITEMREEMBOLSO")
    val items: List<ItemReembolso>
)

@Entity
@Table(name = "item_reembolso")
@EntityListeners(AuditingEntityListener::class)
@NoArgConstructor
data class ItemReembolso(
    @Id
    @Column(name = "id")
    val id: String = UUID.randomUUID().toString(),

    @Column(name = "id_vereador")
    @JacksonXmlProperty(localName = "Chave")
    val idVereador: String,

    @Column(name = "nome_vereador")
    @JacksonXmlProperty(localName = "VEREADOR")
    val nomeVereador: String,

    @Column(name = "id_centro_custo")
    @JacksonXmlProperty(localName = "CENTROCUSTOSID")
    val idCentroCusto: Double,

    @Column(name = "departamento")
    @JacksonXmlProperty(localName = "DEPARTAMENTO")
    val departamento: String,

    @Column(name = "tipo_departamento")
    @JacksonXmlProperty(localName = "TIPODEPARTAMENTO")
    val tipoDepartamento: Int,

    @Column(name = "ano")
    @JacksonXmlProperty(localName = "ANO")
    val ano: Int,

    @Column(name = "mes")
    @JacksonXmlProperty(localName = "MES")
    val mes: Int,

    @Column(name = "nome_despesa")
    @JacksonXmlProperty(localName = "DESPESA")
    val nomeDespesa: String,

    @Column(name = "id_despesa")
    val idDespesa: String? = null,

    @Column(name = "cnpj")
    @JacksonXmlProperty(localName = "CNPJ")
    val cnpj: String,

    @Column(name = "fornecedor")
    @JacksonXmlProperty(localName = "FORNECEDOR")
    val fornecedor: String,

    @Column(name = "valor")
    @JacksonXmlProperty(localName = "VALOR")
    val valor: Double,

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    var createdDate: ZonedDateTime = ZonedDateTime.now(),

    @LastModifiedDate
    @Column(name = "modified_time")
    var modifiedDate: ZonedDateTime = ZonedDateTime.now()
)
