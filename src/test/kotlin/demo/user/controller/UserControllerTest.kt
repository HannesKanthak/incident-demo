package demo.user.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import demo.RestExceptionHandler
import demo.user.dto.api.UserRequest
import demo.user.dto.api.UserResponse
import demo.user.model.UserRole
import demo.user.service.UserService
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime

class UserControllerTest : StringSpec({

    val service = mockk<UserService>()
    val objectMapper = jacksonObjectMapper()
    val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(UserController(service))
        .setControllerAdvice(RestExceptionHandler())
        .build()

    "should create user via POST" {
        val request = UserRequest("john", "john@example.com", UserRole.USER)
        val response = UserResponse(
            id = 1L,
            username = request.username,
            email = request.email,
            role = request.role.name,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        every { service.createUser(request) } returns response

        val result = mockMvc.perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andReturn()

        verify { service.createUser(request) }
        objectMapper.readValue(result.response.contentAsString, UserResponse::class.java) shouldBe response
    }

    "should list users via GET" {
        val response = UserResponse(
            id = 1L,
            username = "john",
            email = "john@example.com",
            role = UserRole.USER.name,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        every { service.getUsers() } returns listOf(response)

        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk)

        verify { service.getUsers() }
    }
})
