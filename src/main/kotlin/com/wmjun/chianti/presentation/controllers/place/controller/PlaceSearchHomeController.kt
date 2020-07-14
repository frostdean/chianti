package com.wmjun.chianti.presentation.controllers.place.controller

import com.wmjun.chianti.application.hotkeyword.service.HotKeywordService
import com.wmjun.chianti.presentation.controllers.place.dto.hotkeyword.HotKeywordResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PlaceSearchHomeController(private val hotKeywordService: HotKeywordService) {

    @GetMapping
    fun getMainPage(model: Model): String {
        val hotKeywords = hotKeywordService.getTop10Keyword().map {
            HotKeywordResponse.fromHotKeyword(it)
        }
        model.addAttribute("hotKeywords", hotKeywords)

        return "main"
    }

}