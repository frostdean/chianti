package com.wmjun.chianti.application.user.service

import com.wmjun.chianti.domain.user.User
import com.wmjun.chianti.domain.user.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserServiceImpl(private val userRepository: UserRepository,
                      private val passwordEncoder: PasswordEncoder) : UserService {

    override fun signUp(email: String, pw: String): User {
        return userRepository.save(User(email = email, pw = passwordEncoder.encode(pw)))
    }

    override fun loadUserByUsername(email: String): User {
        return userRepository.findByEmail(email) ?: throw RuntimeException()
    }
}