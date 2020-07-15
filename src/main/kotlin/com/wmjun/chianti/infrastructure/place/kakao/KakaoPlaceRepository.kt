package com.wmjun.chianti.infrastructure.place.kakao

import com.wmjun.chianti.domain.place.converter.PlaceGroupConverter
import com.wmjun.chianti.domain.place.model.Coordinates
import com.wmjun.chianti.domain.place.model.Place
import com.wmjun.chianti.domain.place.repositories.PlaceRepository
import com.wmjun.chianti.infrastructure.pagination.Page
import com.wmjun.chianti.infrastructure.pagination.Pagination
import com.wmjun.chianti.infrastructure.place.kakao.dto.KakaoPlaceResponse
import com.wmjun.chianti.presentation.exception.ExternalApiException
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import retrofit2.Response

private const val KAKAO_MAP_URL = "https://map.kakao.com/link/map"

@Repository
class KakaoPlaceRepository(private val kakaoPlaceClient: KakaoPlaceClient) : PlaceRepository {

    @Cacheable(cacheNames = ["kakao-place"], key = "#keyword +':'+ #page +':'+ #pageSize")
    override fun search(keyword: String, page: Int, pageSize: Int): Page<Place> {

        val apiResult = kakaoPlaceClient.findByKeyword(keyword, page, pageSize).execute()
        if (!apiResult.isSuccessful) {
            throw ExternalApiException("kakao search api response error. ${apiResult.errorBody()}")
        }

        return makePlacePage(apiResult, page, pageSize)
    }

    private fun makePlacePage(apiResult: Response<KakaoPlaceResponse>, page: Int, pageSize: Int): Page<Place> {

        val placeList = apiResult.body()?.documents?.map {
            Place(name = it.placeName,
                    group = PlaceGroupConverter.fromKakaoCategory(it.categoryGroupCode),
                    phone = it.phone,
                    address = it.addressName,
                    roadAddress = it.roadAddressName,
                    coordinates = Coordinates(it.x, it.y),
                    externalMapUrl = "$KAKAO_MAP_URL/${it.id}")
        } ?: emptyList()

        val pagination = apiResult.body()?.meta?.let {
            Pagination(page, pageSize, it.pageableCount, !it.isEnd)
        }

        return Page(placeList, pagination!!)
    }
}