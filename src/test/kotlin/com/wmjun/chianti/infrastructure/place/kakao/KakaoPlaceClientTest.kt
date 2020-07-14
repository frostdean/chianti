package com.wmjun.chianti.infrastructure.place.kakao

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
internal class KakaoPlaceClientTest {

    @Autowired
    lateinit var kakaoPlaceClient: KakaoPlaceClient

    //    @RepeatedTest(5)
    @Test
    @DisplayName("정상적인 키워드로 검색 하는 경우")
    fun testValidRequest() {
        val result = kakaoPlaceClient.findByKeyword("서울시청", 1, 15).execute()

        assertTrue(result.isSuccessful)
        assertNotNull(result.body())
    }

    @Test
    @DisplayName("유효하지 않은 키워드로 검색하는 경우")
    fun testBadKeywordRequest() {
        val result = kakaoPlaceClient.findByKeyword("", 1, 15).execute()

        assertFalse(result.isSuccessful)
        assertNotNull(result.errorBody())
    }

}