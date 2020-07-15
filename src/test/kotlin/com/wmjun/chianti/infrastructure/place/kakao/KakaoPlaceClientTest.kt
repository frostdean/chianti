package com.wmjun.chianti.infrastructure.place.kakao

import com.wmjun.chianti.TestWithApplicationContext
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired


internal class KakaoPlaceClientTest : TestWithApplicationContext() {

    @Autowired
    private lateinit var kakaoPlaceClient: KakaoPlaceClient

    @ParameterizedTest
    @ValueSource(strings = ["서울시청", "카카오", "제주시청"])
    @DisplayName("정상적인 키워드로 검색 하는 경우")
    fun testValidRequest(keyword: String) {
        val result = kakaoPlaceClient.findByKeyword(keyword, 1, 15).execute()

        assertTrue(result.isSuccessful)
        assertNotNull(result.body())
    }

    @Test
    @DisplayName("유효하지 않은 키워드로 검색하는 경우")
    fun testBadKeywordRequest() {
        val result = kakaoPlaceClient.findByKeyword("", 1, 15).execute()

        println(result.body())

        assertFalse(result.isSuccessful)
        assertNotNull(result.errorBody())
    }

}