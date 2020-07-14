package com.wmjun.chianti.presentation.controllers.place.controller

import com.wmjun.chianti.application.common.DEFAULT_PAGE
import com.wmjun.chianti.application.hotkeyword.service.HotKeywordService
import com.wmjun.chianti.domain.place.repositories.PlaceRepository
import com.wmjun.chianti.presentation.controllers.common.dto.ChiantiResponse
import com.wmjun.chianti.presentation.controllers.common.dto.ResponseMeta
import com.wmjun.chianti.presentation.controllers.place.dto.place.PlaceResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class PlaceSearchController(private val kakaoPlaceRepository: PlaceRepository,
                            private val hotKeywordService: HotKeywordService) {

    private val logger = LoggerFactory.getLogger(PlaceSearchController::class.java)

    @GetMapping("/places")
    fun getPlaceByKeyword(@RequestParam keyword: String, @RequestParam(required = false) page: Int?, model: Model): String {
        page ?: hotKeywordService.increaseSearchCount(keyword)

        val result = kakaoPlaceRepository.search(keyword, page ?: DEFAULT_PAGE)?.let {
            ChiantiResponse.success(
                    data = it.data.map { place -> PlaceResponse.fromPlace(place) },
                    meta = ResponseMeta(it.pagination)
            )
        } ?: ChiantiResponse.notFound()

        model.addAttribute("keyword", keyword)
        model.addAttribute("result", result)

        return "list"
    }

}