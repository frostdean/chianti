package com.wmjun.chianti.domain.place.repositories

import com.wmjun.chianti.application.common.DEFAULT_PAGE
import com.wmjun.chianti.application.common.DEFAULT_PAGE_SIZE
import com.wmjun.chianti.application.common.Page
import com.wmjun.chianti.domain.place.model.Place

interface PlaceRepository {
    fun search(keyword: String, page: Int = DEFAULT_PAGE, pageSize: Int = DEFAULT_PAGE_SIZE): Page<Place>?
}