package com.wmjun.chianti.presentation.controllers.place.controller

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.wmjun.chianti.application.hotkeyword.service.HotKeywordService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.client.match.MockRestRequestMatchers.content
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(PlaceSearchHomeController::class)
@ExtendWith(SpringExtension::class)
internal class PlaceSearchHomeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var hotKeywordService: HotKeywordService

    @Test
    @DisplayName("로그인 하지 않고 키워드로 컨트롤러 호출")
    fun testNotLoginUser() {
        whenever(hotKeywordService.getTop10Keyword()).then { }

        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    @WithMockUser("ALL")
    @DisplayName("메인 페이지 호출")
    fun testGetHome() {
        whenever(hotKeywordService.getTop10Keyword()).then { }

        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect {
                    verify(hotKeywordService, atLeastOnce()).increaseSearchCount(any())
                }
                .andExpect {
                    content().string("장소를 검색해 보세요")
                }
    }

    @Test
    @WithMockUser("ALL")
    @DisplayName("키워드 노출 에러시에도 정상 노출")
    fun testGetHomeButKeywordError() {
        whenever(hotKeywordService.getTop10Keyword()).thenThrow(RuntimeException())

        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect {
                    content().string("장소를 검색해 보세요")
                }
    }


}