package com.wmjun.chianti.presentation.controllers.place.dto.hotkeyword

import com.wmjun.chianti.domain.hotkeyword.model.HotKeyword

data class HotKeywordResponse(
        val keyword: String,
        val count: Long
) {
    companion object {
        fun fromHotKeyword(hotKeyword: HotKeyword) = HotKeywordResponse(hotKeyword.keyword, hotKeyword.searchCount)
    }
}