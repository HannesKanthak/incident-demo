package demo.hello.controller

import demo.RestExceptionHandler
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class HelloControllerTest : StringSpec({

    val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(HelloController())
        .setControllerAdvice(RestExceptionHandler())
        .build()

    "should return hello world message" {
        val result = mockMvc.perform(get("/api/hello"))
            .andExpect(status().isOk)
            .andReturn()

        result.response.contentAsString shouldBe "Hello World! This is the Incident-Demo!"
    }
})
