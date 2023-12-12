package com.quorum.api.repositories

import com.quorum.api.models.Fornecedor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FornecedorRepository : CrudRepository<Fornecedor, String>
