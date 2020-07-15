package com.wmjun.chianti.domain.place.converter

import com.wmjun.chianti.domain.place.model.PlaceGroup
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class PlaceGroupConverterTest {

    @Test
    @DisplayName("값이 카테고리에 있을 경우")
    fun testConverting(){
        //given
        val value = "CS2"

        //when
        val result = PlaceGroupConverter.fromKakaoCategory(value)

        //then
        assertEquals(result, PlaceGroup.CVS)
    }

    @Test
    @DisplayName("값이 카테고리에 없을 경우")
    fun testConvertingUnknown(){
        //given
        val value = "SAFSDFDSFDSFDS"

        //when
        val result = PlaceGroupConverter.fromKakaoCategory(value)

        //then
        assertEquals(result, PlaceGroup.ETC)
    }
}