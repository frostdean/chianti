package com.wmjun.chianti

import com.wmjun.chianti.infrastructure.place.kakao.KakaoPlaceClient
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class ChiantiApplicationTests {

    @Autowired
    lateinit var applicationContext: ApplicationContext

    @Autowired
    lateinit var kakaoPlaceClient: KakaoPlaceClient

    @Test
    fun contextLoads() {
        assertNotNull(applicationContext)
        assertNotNull(kakaoPlaceClient)
    }
}
