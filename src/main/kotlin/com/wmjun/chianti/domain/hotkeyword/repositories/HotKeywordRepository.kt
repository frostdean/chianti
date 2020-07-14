package com.wmjun.chianti.domain.hotkeyword.repositories

import com.wmjun.chianti.domain.hotkeyword.model.HotKeyword
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface HotKeywordRepository : JpaRepository<HotKeyword, String> {
    fun findByKeyword(keyword: String): HotKeyword?
    fun findTop10ByUpdatedAtAfterOrderBySearchCountDesc(after: LocalDateTime): List<HotKeyword>
}