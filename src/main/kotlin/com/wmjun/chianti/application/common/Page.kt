package com.wmjun.chianti.application.common

data class Page<T>(
        val data: Collection<T>,
        val pagination: Pagination
)