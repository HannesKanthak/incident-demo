package demo.user.model

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val username: String,
    val email: String,
    @Enumerated(EnumType.STRING)
    val role: UserRole,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    @Version
    val version: Long = 0L
)

enum class UserRole {
    ADMIN,
    USER
}
