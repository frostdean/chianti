package com.wmjun.chianti.application.place.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.wmjun.chianti.domain.place.model.Coordinates
import com.wmjun.chianti.domain.place.model.Place
import com.wmjun.chianti.domain.place.model.PlaceGroup
import com.wmjun.chianti.domain.place.repositories.PlaceRepository
import com.wmjun.chianti.infrastructure.pagination.Page
import com.wmjun.chianti.infrastructure.pagination.Pagination
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class PlaceServiceImplTest {

    private val placeRepository: PlaceRepository = mock()
    private val sut = PlaceServiceImpl(placeRepository)

    @Test
    @DisplayName("장소 검색 결과가 있을 때")
    fun testSearch() {
        //given
        val placeList = listOf(
                Place("밥집", PlaceGroup.FOOD, "123", "서울", "서울로", Coordinates(0.0, 0.0), "none"),
                Place("밥집2", PlaceGroup.FOOD, "124", "서울", "서울로", Coordinates(0.0, 0.0), "none")
        )
        val pagination = Pagination(1, 15, 2, false)

        //when
        whenever(placeRepository.search(any(), any(), any())).thenReturn(Page(placeList, pagination))

        val result = sut.getPlacesByKeyword("test", 1, 1)

        //then
        assertTrue(result.data.isNotEmpty())
    }


    @Test
    @DisplayName("장소 검색 결과가 없을 때")
    fun testSearchEmptyResult() {
        //given
        val placeList = listOf<Place>()
        val pagination = Pagination(1, 15, 0, false)

        //when
        whenever(placeRepository.search(any(), any(), any())).thenReturn(Page(placeList, pagination))

        val result = sut.getPlacesByKeyword("test", 1, 1)

        //then
        assertTrue(result.data.isEmpty())
    }


}