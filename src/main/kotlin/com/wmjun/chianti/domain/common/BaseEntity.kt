package com.wmjun.chianti.domain.common

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity(

        @CreatedDate
        @Column(name = "created_at", columnDefinition = "DATETIME")
        open val createdAt: LocalDateTime,

        @LastModifiedDate
        @Column(name = "updated_at", columnDefinition = "DATETIME")
        open var updatedAt: LocalDateTime
)