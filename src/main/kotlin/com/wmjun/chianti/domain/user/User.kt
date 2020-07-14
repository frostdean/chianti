package com.wmjun.chianti.domain.user

import com.wmjun.chianti.domain.common.BaseEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        val userId: Long? = null,

        @Column(name = "login_id", nullable = false, length = 255)
        val loginId: String,

        @Column(name = "password", nullable = false, length = 255)
        var pw: String,

        override val createdAt: LocalDateTime = LocalDateTime.now(),
        override var updatedAt: LocalDateTime = LocalDateTime.now()
) : BaseEntity(createdAt, updatedAt), UserDetails {

    override fun getUsername(): String = this.loginId
    override fun getPassword(): String = this.pw

    /**
     * 간단한 유저 구현을 위하여 아래 기능은 사용하지 않는다.
     */
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableSetOf(SimpleGrantedAuthority("ALL"))
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isAccountNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
}