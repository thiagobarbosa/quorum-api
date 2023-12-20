package com.quorum.api.despesas.modelos

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

@Entity
@Table(name = "despesa")
@EntityListeners(AuditingEntityListener::class)
@NoArgConstructor
data class Despesa(
    @Id
    @Column(name = "id")
    val id: String = UUID.randomUUID().toString(),

    @Column(name = "nome_categoria")
    val nomeCategoria: String,

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    var createdDate: ZonedDateTime = ZonedDateTime.now(),

    @LastModifiedDate
    @Column(name = "modified_time")
    var modifiedDate: ZonedDateTime = ZonedDateTime.now()
)
