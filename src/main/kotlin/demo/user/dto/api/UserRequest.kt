package demo.user.dto.api

import demo.user.model.UserRole
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UserRequest(
    @field:NotBlank val username: String,
    @field:Email val email: String,
    @field:NotNull val role: UserRole
)
