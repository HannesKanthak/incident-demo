package demo.user.controller

import demo.user.dto.api.UserRequest
import demo.user.dto.api.UserResponse
import demo.user.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(private val service: UserService) {

    @PostMapping
    fun create(@Valid @RequestBody body: UserRequest): UserResponse = service.createUser(body)

    @GetMapping
    fun list(): List<UserResponse> = service.getUsers()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): UserResponse = service.getUser(id)
}
