package com.wmjun.chianti.infrastructure.pagination

data class Page<T>(
        val data: Collection<T>,
        val pagination: Pagination
)