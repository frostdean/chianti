package com.wmjun.chianti.application.user

import com.wmjun.chianti.application.user.service.UserService
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component

@Component
class SampleUserLoader(
        private val userService: UserService
) : InitializingBean {
    override fun afterPropertiesSet() {
        userService.signUp("test1@test.com", "1234")
        userService.signUp("test2@test.com", "1234")
    }
}