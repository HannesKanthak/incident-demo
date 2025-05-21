package demo.user.service

import demo.user.dto.api.UserRequest
import demo.user.dto.api.UserResponse
import demo.user.dto.api.toResponseDto
import demo.user.model.User
import demo.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UserService(private val repository: UserRepository) {

    @Transactional
    fun createUser(request: UserRequest): UserResponse {
        val now = LocalDateTime.now()
        val user = User(
            username = request.username,
            email = request.email,
            role = request.role,
            createdAt = now,
            updatedAt = now
        )
        val saved = repository.save(user)
        return saved.toResponseDto()
    }

    fun getUsers(): List<UserResponse> = repository.findAll().map { it.toResponseDto() }

    fun getUser(id: Long): UserResponse =
        repository.findById(id).orElseThrow { IllegalArgumentException("User $id not found") }
            .toResponseDto()
}
