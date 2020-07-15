package com.wmjun.chianti.domain.place.repositories

import com.wmjun.chianti.domain.place.model.Place
import com.wmjun.chianti.infrastructure.pagination.DEFAULT_PAGE
import com.wmjun.chianti.infrastructure.pagination.DEFAULT_PAGE_SIZE
import com.wmjun.chianti.infrastructure.pagination.Page

interface PlaceRepository {
    fun search(keyword: String, page: Int = DEFAULT_PAGE, pageSize: Int = DEFAULT_PAGE_SIZE): Page<Place>
}