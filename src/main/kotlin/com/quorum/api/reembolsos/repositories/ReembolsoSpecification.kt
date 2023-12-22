package com.quorum.api.reembolsos.repositories

import com.quorum.api.reembolsos.modelos.Reembolso
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class ReembolsoSpecification(
    private val idVereador: String?,
    private val idDespesa: String?,
    private val cnpj: String?,
    private val ano: Int?,
    private val mes: Int?
) : Specification<Reembolso> {

    override fun toPredicate(root: Root<Reembolso>, query: CriteriaQuery<*>, cb: CriteriaBuilder): Predicate? {
        val predicates = mutableListOf<Predicate>()

        idVereador?.let {
            predicates.add(cb.equal(root.get<String>("idVereador"), it))
        }

        idDespesa?.let {
            predicates.add(cb.equal(root.get<String>("idDespesa"), it))
        }

        cnpj?.let {
            predicates.add(cb.equal(root.get<String>("cnpj"), it))
        }

        ano?.let {
            predicates.add(cb.equal(root.get<Int>("ano"), it))
        }

        mes?.let {
            predicates.add(cb.equal(root.get<Int>("mes"), it))
        }

        return cb.and(*predicates.toTypedArray())
    }
}
