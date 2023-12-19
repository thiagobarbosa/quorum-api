package com.quorum.api.vereadores.modelos

import com.quorum.api.annotations.NoArgConstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "vereador")
@EntityListeners(AuditingEntityListener::class)
@NoArgConstructor
data class Vereador(
    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "nome")
    val nome: String,

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    var createdDate: ZonedDateTime = ZonedDateTime.now(),

    @LastModifiedDate
    @Column(name = "modified_time")
    var modifiedDate: ZonedDateTime = ZonedDateTime.now()
)
