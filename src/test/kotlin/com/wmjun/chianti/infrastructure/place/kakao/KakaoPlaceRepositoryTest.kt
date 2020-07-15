package com.wmjun.chianti.infrastructure.place.kakao

import com.wmjun.chianti.TestWithApplicationContext
import com.wmjun.chianti.presentation.exception.ExternalApiException
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class KakaoPlaceRepositoryTest : TestWithApplicationContext() {

    @Autowired
    private lateinit var kakaoPlaceClient: KakaoPlaceClient

    private lateinit var sut: KakaoPlaceRepository

    @BeforeAll
    fun setUp() {
        sut = KakaoPlaceRepository(kakaoPlaceClient)
    }

    @ParameterizedTest
    @ValueSource(strings = ["우리은행", "제주시청", "국립박물관"])
    @DisplayName("키워드로 장소를 얻어올 때")
    fun testPlaceByKeyword(keyword: String) {

        //when
        val result = sut.search(keyword)

        //then
        assertTrue(result.data.isNotEmpty())
        assertEquals(result.pagination.pageSize, 15)
    }

    @Test
    @DisplayName("externalAPI에서 처리할 수 없는 키워드를 줬을 때")
    fun testPlaceByKeyword() {
        assertThrows<ExternalApiException> { sut.search("") }

    }

    @Test
    @DisplayName("장소가 없을 때")
    fun testUnknownPlaceByKeyword() {
        //given
        val keyword = "ㅏㅏ&🧵ㅣㅁ"

        //when
        val result = sut.search(keyword)

        //then
        assertTrue(result.data.isEmpty())

    }


}