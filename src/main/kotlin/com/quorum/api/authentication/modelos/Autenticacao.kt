package com.quorum.api.authentication.modelos

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
@Table(name = "autenticacao")
@EntityListeners(AuditingEntityListener::class)
@NoArgConstructor
class Autenticacao(
    @Id
    @Column(name = "token")
    val token: String,

    @Column(name = "data_expiracao")
    val dataExpiracao: ZonedDateTime,

    @Column(name = "role")
    val role: String,

    @Column(name = "email")
    val email: String? = null,

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    var createdDate: ZonedDateTime = ZonedDateTime.now(),

    @LastModifiedDate
    @Column(name = "modified_time")
    var modifiedDate: ZonedDateTime = ZonedDateTime.now()
)

fun Autenticacao.obterLimiteRequisicao(): Int {
    return when (role) {
        "PUBLIC" -> 10
        "USER" -> 100
        "ADMIN" -> 1000
        else -> 0
    }
}
