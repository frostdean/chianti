package com.wmjun.chianti.infrastructure.place.kakao

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Configuration
@EnableConfigurationProperties(KakaoPlaceConfiguration.HttpConfig::class)
class KakaoPlaceConfiguration {

    @Bean
    fun kakaoHttpClient(@Value("\${kakao.api.key}") apiKey: String, httpConfig: HttpConfig): OkHttpClient =
            OkHttpClient.Builder()
                    .callTimeout(httpConfig.callTimeout, TimeUnit.MILLISECONDS)
                    .connectTimeout(httpConfig.connectionTimeout, TimeUnit.MILLISECONDS)
                    .readTimeout(httpConfig.readTimeout, TimeUnit.MILLISECONDS)
                    .writeTimeout(httpConfig.writeTimeout, TimeUnit.MILLISECONDS)
                    .addInterceptor {
                        it.proceed(
                                it.request().newBuilder()
                                        .addHeader(HttpHeaders.AUTHORIZATION, apiKey)
                                        .build()
                        )
                    }.build()


    @Bean
    fun kakaoPlaceClient(kakaoHttpClient: OkHttpClient, @Value("\${kakao.api.base-url}") baseUrl: String): KakaoPlaceClient =
            Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper().findAndRegisterModules()))
                    .client(kakaoHttpClient)
                    .build()
                    .create(KakaoPlaceClient::class.java)


    @ConfigurationProperties("kakao.api.timeout")
    @ConstructorBinding
    data class HttpConfig(val callTimeout: Long,
                          val connectionTimeout: Long,
                          val readTimeout: Long,
                          val writeTimeout: Long
    )
}