package demo.user.dto.api

import demo.user.model.User
import demo.user.model.UserRole
import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
    val role: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
)

fun User.toResponseDto() = UserResponse(
    id = id,
    username = username,
    email = email,
    role = role.name,
    createdAt = createdAt,
    updatedAt = updatedAt
)
