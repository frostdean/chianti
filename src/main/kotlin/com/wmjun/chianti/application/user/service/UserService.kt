package com.wmjun.chianti.application.user.service

import com.wmjun.chianti.domain.user.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {
    fun signUp(loginId: String, pw: String): User
    override fun loadUserByUsername(username: String): User
}