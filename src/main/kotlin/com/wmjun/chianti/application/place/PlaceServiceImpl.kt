package com.wmjun.chianti.application.place

import com.wmjun.chianti.domain.place.model.Place
import com.wmjun.chianti.domain.place.repositories.PlaceRepository
import com.wmjun.chianti.infrastructure.pagination.Page
import org.springframework.stereotype.Service

@Service
class PlaceServiceImpl(val placeRepository: PlaceRepository) : PlaceService {
    override fun getPlacesByKeyword(keyword: String, page: Int, pageSize: Int): Page<Place>? {
        return placeRepository.search(keyword, page, pageSize)
    }
}
