package com.quorum.api.fornecedores.modelos

import com.quorum.api.annotations.NoArgConstructor
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "fornecedor")
@EntityListeners(AuditingEntityListener::class)
@NoArgConstructor
data class Fornecedor(
    @Id
    @Column(name = "cnpj")
    val cnpj: String,

    @Column(name = "nome")
    val nome: String
)
