package com.wmjun.chianti.application.user.service

import com.wmjun.chianti.TestWithApplicationContext
import com.wmjun.chianti.presentation.exception.UserNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

internal class UserServiceImplTest : TestWithApplicationContext() {

    @Autowired
    private lateinit var sut: UserService

    @Test
    @DisplayName("가입 테스트")
    fun testSignUp() {

        //when
        val result = sut.signUp("test-user", "my-pass")

        //then
        assertNotNull(result)
        assertEquals(result.loginId, "test-user")
    }

    @Test
    @DisplayName("유저 검색 테스트")
    fun testLoadUserByUsername() {
        //given
        /**
         * userService.signUp("ryan", "chianti")
         * userService.signUp("kon", "chianti")
         *
         * at SampleUserLoader.class
         */

        //when
        val result = sut.loadUserByUsername("ryan")

        //then

        assertNotNull(result)
        assertEquals(result.loginId, "ryan")
    }

    @Test
    @DisplayName("존재하지 않는 유저 검색 테스트")
    fun testLoadUserByUsernameNotFound() {
        //given
        /**
         * userService.signUp("ryan", "chianti")
         * userService.signUp("kon", "chianti")
         *
         * at SampleUserLoader.class
         */

        assertThrows<UserNotFoundException> { sut.loadUserByUsername("unknown") }
    }


}