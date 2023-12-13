package com.quorum.api.redisflag

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RepositorioRedisCacheFlag : CrudRepository<RedisCacheFlag, String>
