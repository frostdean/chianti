package com.wmjun.chianti.domain.user.repositories

import com.wmjun.chianti.TestWithApplicationContext
import com.wmjun.chianti.domain.user.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


internal class UserRepositoryTest : TestWithApplicationContext() {

    @Autowired
    private lateinit var sut: UserRepository

    @BeforeEach
    fun cleanUp() {
        sut.deleteAll()
    }

    @Test
    @DisplayName("유저 저장 테스트")
    fun testSaveUser() {
        //given
        val user = User(loginId = "asdf", pw = "zxcv")
        //when
        val result = sut.save(user)
        //then
        assertEquals(user, result)
    }

    @Test
    @DisplayName("유저 조회 테스트")
    fun testFindUser() {
        //given
        val user = User(loginId = "asdf", pw = "zxcv")
        sut.save(user)
        //when
        val result = sut.findByLoginId("asdf")
        //then
        assertNotNull(result)
        assertNotNull(result?.userId)
        assertEquals(user, result)
    }

    @Test
    @DisplayName("존재하지 않는 유저 조회 테스트")
    fun testUserNotFound() {
        //given
        val user = User(loginId = "asdf", pw = "zxcv")
        sut.save(user)
        //when
        val result = sut.findByLoginId("asdfsdf")
        //then
        assertNull(result)
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    fun testDeleteUser() {
        //유저 저장 후 조회
        val user = User(loginId = "asdf", pw = "zxcv")
        sut.save(user)

        val result = sut.findByLoginId("asdf")
        assertNotNull(result)

        //유저 삭제 후 조회
        result?.userId?.let { sut.deleteById(it) }
        assertNull(sut.findByLoginId("asdf"))

    }

}