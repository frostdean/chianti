package com.wmjun.chianti.domain.user.repositories

import com.wmjun.chianti.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}