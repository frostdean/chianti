package com.wmjun.chianti.presentation.controllers.place.controller

import com.nhaarman.mockitokotlin2.*
import com.wmjun.chianti.application.hotkeyword.service.HotKeywordService
import com.wmjun.chianti.application.place.service.PlaceService
import com.wmjun.chianti.domain.place.model.Coordinates
import com.wmjun.chianti.domain.place.model.Place
import com.wmjun.chianti.domain.place.model.PlaceGroup
import com.wmjun.chianti.infrastructure.pagination.Page
import com.wmjun.chianti.infrastructure.pagination.Pagination
import com.wmjun.chianti.presentation.controllers.advice.DEFAULT_CLIENT_ERROR_MSG
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
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

@WebMvcTest(PlaceSearchController::class)
@ExtendWith(SpringExtension::class)
internal class PlaceSearchControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var placeService: PlaceService

    @MockBean
    private lateinit var hotKeywordService: HotKeywordService

    @Test
    @DisplayName("로그인 하지 않고 키워드로 컨트롤러 호출")
    fun testNotLoginUser() {
        whenever(hotKeywordService.increaseSearchCount(any())).then { }
        whenever(placeService.getPlacesByKeyword(any(), any(), any())).thenReturn(Page(
                listOf(Place("밥집", PlaceGroup.FOOD, "123", "서울", "서울로", Coordinates(0.0, 0.0), "none")),
                Pagination(1, 15, 1, false)
        ))

        mockMvc.perform(MockMvcRequestBuilders.get("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .param("keyword", "맥도날드"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    @DisplayName("키워드로 장소조회 컨트롤러 테스트")
    @WithMockUser("ALL")
    fun testGetPlaceByKeyword() {

        whenever(hotKeywordService.increaseSearchCount(any())).then { }
        whenever(placeService.getPlacesByKeyword(any(), any(), any())).thenReturn(Page(
                listOf(Place("밥집", PlaceGroup.FOOD, "123", "서울", "서울로", Coordinates(0.0, 0.0), "none")),
                Pagination(1, 15, 1, false)
        ))

        mockMvc.perform(MockMvcRequestBuilders.get("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .param("keyword", "맥도날드"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect {
                    verify(hotKeywordService, atLeastOnce()).increaseSearchCount(any())
                    verify(placeService, atLeastOnce()).getPlacesByKeyword(any(), any(), any())
                }.andExpect {
                    content().string("검색 결과")
                }
    }

    @Test
    @DisplayName("파라메타 누락하고 호출")
    @WithMockUser("ALL")
    fun testGetPlaceByKeywordWithoutParams() {

        whenever(hotKeywordService.increaseSearchCount(any())).then { }
        whenever(placeService.getPlacesByKeyword(any(), any(), any())).thenReturn(Page(
                listOf(Place("밥집", PlaceGroup.FOOD, "123", "서울", "서울로", Coordinates(0.0, 0.0), "none")),
                Pagination(1, 15, 1, false)
        ))

        mockMvc.perform(MockMvcRequestBuilders.get("/places")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect {
                    content().string(DEFAULT_CLIENT_ERROR_MSG)
                }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "   ", "\n", "\t"])
    @DisplayName("키워드 밸리데이션 실패")
    @WithMockUser("ALL")
    fun testGetPlaceByKeywordWithInvalidKeyword(keyword: String) {

        whenever(hotKeywordService.increaseSearchCount(any())).then { }
        whenever(placeService.getPlacesByKeyword(any(), any(), any())).thenReturn(Page(
                listOf(Place("밥집", PlaceGroup.FOOD, "123", "서울", "서울로", Coordinates(0.0, 0.0), "none")),
                Pagination(1, 15, 1, false)
        ))

        mockMvc.perform(MockMvcRequestBuilders.get("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .param("keyword", keyword))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect {
                    content().string("유효하지 않은 키워드 입니다")
                }
    }

    @Test
    @DisplayName("페이지를 넘길때는 키워드 카운트를 올리지 않는다")
    @WithMockUser("ALL")
    fun testDoNotIncreaseKeywordIfChangePage() {

        whenever(hotKeywordService.increaseSearchCount(any())).then { }
        whenever(placeService.getPlacesByKeyword(any(), any(), any())).thenReturn(Page(
                listOf(Place("밥집", PlaceGroup.FOOD, "123", "서울", "서울로", Coordinates(0.0, 0.0), "none")),
                Pagination(1, 15, 1, false)
        ))

        mockMvc.perform(MockMvcRequestBuilders.get("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .param("keyword", "밥집")
                .param("page", "2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect {
                    verify(hotKeywordService, times(0)).increaseSearchCount(any())
                    verify(placeService, atLeastOnce()).getPlacesByKeyword(any(), any(), any())
                }.andExpect {
                    content().string("검색 결과")
                }
    }

    @Test
    @DisplayName("키워드 카운트 올리다 에러 발생시에도 정상 동작")
    @WithMockUser("ALL")
    fun testKeywordCountError() {

        whenever(hotKeywordService.increaseSearchCount(any())).thenThrow(RuntimeException())
        whenever(placeService.getPlacesByKeyword(any(), any(), any())).thenReturn(Page(
                listOf(Place("밥집", PlaceGroup.FOOD, "123", "서울", "서울로", Coordinates(0.0, 0.0), "none")),
                Pagination(1, 15, 1, false)
        ))

        mockMvc.perform(MockMvcRequestBuilders.get("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .param("keyword", "밥집")
                .param("page", "2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect {
                    verify(placeService, atLeastOnce()).getPlacesByKeyword(any(), any(), any())
                }.andExpect {
                    content().string("검색 결과")
                }
    }

    @Test
    @DisplayName("서버 내부 에러 발생")
    @WithMockUser("ALL")
    fun testInternalServerError() {

        whenever(hotKeywordService.increaseSearchCount(any())).then {}
        whenever(placeService.getPlacesByKeyword(any(), any(), any())).thenThrow(RuntimeException())

        mockMvc.perform(MockMvcRequestBuilders.get("/places")
                .contentType(MediaType.APPLICATION_JSON)
                .param("keyword", "밥집")
                .param("page", "2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError)
                .andExpect {
                    content().string("문제가 발생했어요")
                }
    }


}