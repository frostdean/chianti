package com.wmjun.chianti.application.user.service

import com.wmjun.chianti.domain.user.User
import com.wmjun.chianti.domain.user.repositories.UserRepository
import com.wmjun.chianti.presentation.exception.UserNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserServiceImpl(private val userRepository: UserRepository,
                      private val passwordEncoder: PasswordEncoder) : UserService {

    override fun signUp(loginId: String, pw: String): User {
        return userRepository.save(User(loginId = loginId, pw = passwordEncoder.encode(pw)))
    }

    override fun loadUserByUsername(loginId: String): User {
        return userRepository.findByLoginId(loginId) ?: throw UserNotFoundException("Can not found User = $loginId")
    }
}