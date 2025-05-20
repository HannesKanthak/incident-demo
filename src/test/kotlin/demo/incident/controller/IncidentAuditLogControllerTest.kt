package demo.incident.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import demo.RestExceptionHandler
import demo.incident.dto.api.IncidentAuditLogResponse
import demo.incident.service.IncidentAuditLogService
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.domain.Sort
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime

class IncidentAuditLogControllerTest : StringSpec({

    val service = mockk<IncidentAuditLogService>()
    val objectMapper = jacksonObjectMapper()
    val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(IncidentAuditLogController(service))
        .setControllerAdvice(RestExceptionHandler())
        .build()

    "should return audit log for incident" {
        val response = listOf(
            IncidentAuditLogResponse(1L, "OPEN", "RESOLVED", LocalDateTime.now())
        )
        every { service.getAuditLog(42L, Sort.Direction.ASC) } returns response

        val result = mockMvc.perform(get("/api/incidents/42/audit?direction=ASC"))
            .andExpect(status().isOk)
            .andReturn()

        verify { service.getAuditLog(42L, Sort.Direction.ASC) }
        val body = objectMapper.readValue(result.response.contentAsString, Array<IncidentAuditLogResponse>::class.java).toList()
        body shouldContainExactly response
    }
})
