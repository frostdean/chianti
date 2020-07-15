package com.wmjun.chianti.application.hotkeyword.service

import com.wmjun.chianti.domain.hotkeyword.model.HotKeyword

interface HotKeywordService {
    fun increaseSearchCount(keyword: String)
    fun getTop10Keyword(): List<HotKeyword>
}