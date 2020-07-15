package com.wmjun.chianti.infrastructure.place.kakao

import com.wmjun.chianti.infrastructure.place.kakao.dto.KakaoPlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoPlaceClient {

    @GET("/v2/local/search/keyword.json")
    fun findByKeyword(@Query("query") keyword: String, @Query("page") page: Int, @Query("size") size: Int): Call<KakaoPlaceResponse>
}