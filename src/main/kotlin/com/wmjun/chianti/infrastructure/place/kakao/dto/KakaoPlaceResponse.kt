package com.wmjun.chianti.infrastructure.place.kakao.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class KakaoPlaceResponse(
        val meta: Meta,
        val documents: List<KakaoPlace>
) {
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
    data class Meta(
            val totalCount: Int,
            val pageableCount: Int,
            val isEnd: Boolean,
            val sameName: RegionInfo
    )

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
    data class RegionInfo(
            val region: List<String>,
            val keyword: String,
            val selectedRegion: String
    )

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
    data class KakaoPlace(
            val id: String,
            val placeName: String,
            val categoryName: String,
            val categoryGroupCode: String,
            val categoryGroupName: String,
            val phone: String,
            val addressName: String,
            val roadAddressName: String,
            val x: Double,
            val y: Double,
            val placeUrl: String,
            val distance: String
    )
}
