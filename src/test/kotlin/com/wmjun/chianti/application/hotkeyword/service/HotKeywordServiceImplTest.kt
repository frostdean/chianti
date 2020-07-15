package com.wmjun.chianti.application.hotkeyword.service

import com.nhaarman.mockitokotlin2.*
import com.wmjun.chianti.domain.hotkeyword.model.HotKeyword
import com.wmjun.chianti.domain.hotkeyword.repositories.HotKeywordRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class HotKeywordServiceImplTest {

    private val hotKeywordRepository: HotKeywordRepository = mock()
    private val sut = HotKeywordServiceImpl(hotKeywordRepository)

    @Test
    @DisplayName("검색어 카운트 수를 증가시킨다.")
    fun testIncreaseSearchCount() {
        //given
        val keyword = "TEST"
        val hotKeyword = HotKeyword(keyword)

        //when
        whenever(hotKeywordRepository.findByKeyword(keyword)).thenReturn(hotKeyword)
        sut.increaseSearchCount(keyword)
        //then

        assertEquals(hotKeyword.searchCount, 2)
    }

    @Test
    @DisplayName("처음 검색된 키워드인경우")
    fun testIncreaseSearchCountForFirstKeyword() {
        //given
        val keyword = "TEST"

        //when
        whenever(hotKeywordRepository.findByKeyword(keyword)).thenReturn(null)
        whenever(hotKeywordRepository.save(any<HotKeyword>())).thenReturn(HotKeyword(keyword))

        sut.increaseSearchCount(keyword)
        //then
        verify(hotKeywordRepository, times(1)).save(any<HotKeyword>())

    }

    @Test
    @DisplayName("인기 검색어가 있는 경우.")
    fun testGetTop10Keyword() {
        //given
        //when
        whenever(hotKeywordRepository.findTop10ByUpdatedAtAfterOrderBySearchCountDesc(any())).thenReturn(listOf(
                HotKeyword("A"), HotKeyword("B"), HotKeyword("C"), HotKeyword("C")
        ))

        val result = sut.getTop10Keyword()
        //then
        assertTrue(result.isNotEmpty())
    }

    @Test
    @DisplayName("인기 검색어가 아직 없는 경우.")
    fun testGetTop10KeywordEmptyResult() {
        //given
        //when
        whenever(hotKeywordRepository.findTop10ByUpdatedAtAfterOrderBySearchCountDesc(any())).thenReturn(listOf())

        val result = sut.getTop10Keyword()
        //then
        assertTrue(result.isEmpty())
    }

}