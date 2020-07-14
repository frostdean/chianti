package com.wmjun.chianti.application.hotkeyword.service

import com.wmjun.chianti.domain.hotkeyword.model.HotKeyword
import com.wmjun.chianti.domain.hotkeyword.repositories.HotKeywordRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class HotKeywordServiceImpl(private val hotKeywordRepository: HotKeywordRepository) : HotKeywordService {

    @Async
    override fun increaseSearchCount(keyword: String) {
        hotKeywordRepository.findByKeyword(keyword)?.increase() ?: hotKeywordRepository.save(HotKeyword(keyword))
    }

    @Cacheable(cacheNames = ["hot-keyword"], key = "'fixed'")
    override fun getTop10Keyword(): List<HotKeyword> =
            hotKeywordRepository.findTop10ByUpdatedAtAfterOrderBySearchCountDesc(LocalDateTime.now().minusMinutes(10))

}