package com.wmjun.chianti.domain.hotkeyword.repositories

import com.wmjun.chianti.TestWithApplicationContext
import com.wmjun.chianti.domain.hotkeyword.model.HotKeyword
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

internal class HotKeywordRepositoryTest : TestWithApplicationContext() {

    @Autowired
    private lateinit var sut: HotKeywordRepository

    @BeforeEach
    fun cleanUp(){
        sut.deleteAll()
    }

    @Test
    @DisplayName("키워드를 저장할 때")
    fun testSave() {
        //given
        val keyword = "음식점"
        val target = HotKeyword(keyword)

        //when
        val result = sut.save(target)

        //then
        assertNotNull(result)
    }

    @Test
    @DisplayName("키워드 셀렉트를 성공할 때")
    fun testSaveAndSelect() {
        //given
        val keyword = "음식점"
        val target = HotKeyword(keyword)

        //when
        sut.save(target)
        val result = sut.findByKeyword(keyword)

        //then
        assertNotNull(result)
        assertEquals(target, result)
    }

    @Test
    @DisplayName("키워드를 찾을 수 없을 때 ")
    fun testSaveAndSelectFailed() {
        //given
        val keyword = "주유소"
        val target = HotKeyword(keyword)

        //when
        sut.save(target)
        val result = sut.findByKeyword("$keyword!!!!")

        //then
        assertNull(result)
    }

    @Test
    @DisplayName("10분 이내 인기 키워드를 찾을 때")
    fun testFindTop10() {
        //given
        sut.save(HotKeyword("test1", updatedAt = LocalDateTime.now().minusMinutes(5)))
        sut.save(HotKeyword("test2", updatedAt = LocalDateTime.now().minusMinutes(5)))
        sut.save(HotKeyword("test3", updatedAt = LocalDateTime.now().minusMinutes(15)))

        //when
        val result = sut.findTop10ByUpdatedAtAfterOrderBySearchCountDesc(LocalDateTime.now().minusMinutes(10))

        //then
        assertEquals(result.size, 2)
    }

    @Test
    @DisplayName("10분 이내 인기 키워드가 없을 때")
    fun testFindTop10NotFound() {
        //given
        sut.save(HotKeyword("test3", updatedAt = LocalDateTime.now().minusMinutes(15)))

        //when
        val result = sut.findTop10ByUpdatedAtAfterOrderBySearchCountDesc(LocalDateTime.now().minusMinutes(10))

        //then
        assertEquals(result.size, 0)
    }

}