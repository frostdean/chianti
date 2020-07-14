package com.wmjun.chianti.presentation.controllers.place.controller

import com.wmjun.chianti.application.hotkeyword.service.HotKeywordService
import com.wmjun.chianti.application.place.PlaceService
import com.wmjun.chianti.infrastructure.pagination.DEFAULT_PAGE
import com.wmjun.chianti.presentation.controllers.common.dto.ChiantiResponse
import com.wmjun.chianti.presentation.controllers.common.dto.ResponseMeta
import com.wmjun.chianti.presentation.controllers.place.dto.place.PlaceResponse
import com.wmjun.chianti.presentation.exception.InvalidKeywordException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class PlaceSearchController(private val placeService: PlaceService,
                            private val hotKeywordService: HotKeywordService) {

    private val logger = LoggerFactory.getLogger(PlaceSearchController::class.java)

    @GetMapping("/places")
    fun getPlaceByKeyword(@RequestParam keyword: String, @RequestParam(required = false) page: Int?, model: Model): String {
        if (keyword.isBlank()) {
            throw InvalidKeywordException("유효하지 않은 키워드 입니다.")
        }

        page ?: hotKeywordService.increaseSearchCount(keyword)
        val result = getPlaces(keyword, page)

        model.addAttribute("keyword", keyword)
        model.addAttribute("result", result)

        return "list"
    }

    private fun getPlaces(keyword: String, page: Int?): ChiantiResponse<List<PlaceResponse>> {
        return placeService.getPlacesByKeyword(keyword, page ?: DEFAULT_PAGE)
                ?.let {
                    ChiantiResponse.success(
                            data = it.data.map { place -> PlaceResponse.fromPlace(place) },
                            meta = ResponseMeta(it.pagination))
                }
                ?: ChiantiResponse.notFound()
    }

}