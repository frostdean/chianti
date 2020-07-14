package com.wmjun.chianti.domain.hotkeyword.model

import com.wmjun.chianti.domain.common.BaseEntity
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "hot_keywords")
data class HotKeyword(
        @Id
        @Column(name = "keyword", length = 255)
        val keyword: String,

        @Column(name = "search_count", nullable = false)
        var searchCount: Long = 1,
        override val createdAt: LocalDateTime = LocalDateTime.now(),
        override var updatedAt: LocalDateTime = LocalDateTime.now()
) : BaseEntity(createdAt, updatedAt) {

    fun increase() {
        this.searchCount = searchCount.inc()
        this.updatedAt = LocalDateTime.now()
    }
}
