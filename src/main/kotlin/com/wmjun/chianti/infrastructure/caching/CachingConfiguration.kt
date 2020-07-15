package com.wmjun.chianti.infrastructure.caching

import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.context.annotation.Configuration

/**
 * - 로컬 캐시만 적용한 상태 .
 * - 캐시 사용 에러시, 메인 저장소로 요청을 보내고 로그만 남김.
 */
@Configuration
@EnableCaching
class CachingConfiguration : CachingConfigurerSupport() {

    override fun errorHandler(): CacheErrorHandler? {

        return object : CacheErrorHandler {

            private val logger = LoggerFactory.getLogger(this::class.java)

            override fun handleCacheGetError(exception: RuntimeException, cache: Cache, key: Any) {
                logger.error("Cache GET Error. cache : [${cache.name}]", exception)
            }

            override fun handleCacheClearError(exception: RuntimeException, cache: Cache) {
                logger.error("Cache CLEAR Error. cache : [${cache.name}]", exception)
            }

            override fun handleCachePutError(exception: RuntimeException, cache: Cache, key: Any, value: Any?) {
                logger.error("Cache PUT Error. cache : [${cache.name}]", exception)
            }

            override fun handleCacheEvictError(exception: RuntimeException, cache: Cache, key: Any) {
                logger.error("Cache EVICT Error. cache : [${cache.name}]", exception)
            }
        }
    }
}