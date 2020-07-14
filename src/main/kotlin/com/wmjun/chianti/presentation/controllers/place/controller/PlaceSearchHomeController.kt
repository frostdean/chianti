package com.wmjun.chianti.presentation.controllers.place.controller

import com.wmjun.chianti.application.hotkeyword.service.HotKeywordService
import com.wmjun.chianti.presentation.controllers.place.dto.hotkeyword.HotKeywordResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PlaceSearchHomeController(private val hotKeywordService: HotKeywordService) {

    private val logger = LoggerFactory.getLogger(PlaceSearchHomeController::class.java)

    @GetMapping
    fun getMainPage(model: Model): String {
        model.addAttribute("hotKeywords", getHotKeyword())
        return "main"
    }

    /**
     * 인기 검색어 로드가 실패하여도 정상적으로 서비스 되어야한다. 로그만 남기자.
     */
    private fun getHotKeyword(): List<HotKeywordResponse> {
        return try {
            hotKeywordService.getTop10Keyword().map { HotKeywordResponse.fromHotKeyword(it) }
        } catch (e: Exception) {
            logger.error("Hot-Keyword Load Error !", e)
            emptyList()
        }
    }

}