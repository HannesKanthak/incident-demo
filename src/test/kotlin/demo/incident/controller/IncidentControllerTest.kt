package demo.incident.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import demo.RestExceptionHandler
import demo.incident.dto.api.IncidentRequest
import demo.incident.dto.api.IncidentResponse
import demo.incident.dto.api.IncidentStatusUpdate
import demo.incident.model.IncidentSeverity
import demo.incident.model.IncidentStatus
import demo.incident.model.IncidentType
import demo.incident.service.IncidentService
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.data.domain.PageImpl
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime

class IncidentControllerTest : StringSpec({

    val service = mockk<IncidentService>()
    val objectMapper = jacksonObjectMapper()
    val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(IncidentController(service))
        .setControllerAdvice(RestExceptionHandler())
        .build()

    "should create incident via POST" {
        val request = IncidentRequest(
            title = "Test",
            description = "Desc",
            type = IncidentType.AUDIT_FINDING,
            severity = IncidentSeverity.HIGH
        )
        val response = IncidentResponse(
            id = 1L,
            title = request.title,
            description = request.description,
            type = request.type.name,
            severity = request.severity.name,
            status = IncidentStatus.OPEN,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        every { service.createIncident(request) } returns response

        val result = mockMvc.perform(
            post("/api/incidents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isOk)
            .andReturn()

        verify { service.createIncident(request) }
        objectMapper.readValue(result.response.contentAsString, IncidentResponse::class.java) shouldBe response
    }

    "should search incidents via GET" {
        val response = IncidentResponse(
            id = 1L,
            title = "t",
            description = "d",
            type = IncidentType.AUDIT_FINDING.name,
            severity = IncidentSeverity.HIGH.name,
            status = IncidentStatus.OPEN,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        every { service.search(any()) } returns PageImpl(listOf(response))

        mockMvc.perform(get("/api/incidents"))
            .andExpect(status().isOk)

        verify { service.search(any()) }
    }

    "should update status via PATCH" {
        val update = IncidentStatusUpdate(IncidentStatus.RESOLVED)
        val response = IncidentResponse(
            id = 1L,
            title = "t",
            description = "d",
            type = IncidentType.AUDIT_FINDING.name,
            severity = IncidentSeverity.HIGH.name,
            status = update.status,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        every { service.updateStatus(1L, update) } returns response

        mockMvc.perform(
            patch("/api/incidents/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update))
        )
            .andExpect(status().isOk)

        verify { service.updateStatus(1L, update) }
    }
})
