package com.quorum.api.utils

import org.springframework.data.domain.PageRequest
import kotlin.math.min
const val MAX_PAGE_SIZE = 100

fun defaultPageable(page: Int? = 0, pageSize: Int? = MAX_PAGE_SIZE) = PageRequest.of(page ?: 0, min(pageSize ?: MAX_PAGE_SIZE, MAX_PAGE_SIZE))
