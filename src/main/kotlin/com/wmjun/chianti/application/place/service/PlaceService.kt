package com.wmjun.chianti.application.place.service

import com.wmjun.chianti.domain.place.model.Place
import com.wmjun.chianti.infrastructure.pagination.DEFAULT_PAGE_SIZE
import com.wmjun.chianti.infrastructure.pagination.Page

interface PlaceService {

    fun getPlacesByKeyword(keyword: String, page: Int, pageSize: Int = DEFAULT_PAGE_SIZE): Page<Place>
}