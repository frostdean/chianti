package com.wmjun.chianti.infrastructure.place.kakao

import com.wmjun.chianti.infrastructure.place.kakao.dto.KakaoPlace
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoPlaceClient {

    @GET("/v2/local/search/keyword.json")
    fun searchByKeyword(@Query("query") keyword: String): Call<KakaoPlace>
}