package demo

import mu.KotlinLogging
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

private val kLogger = KotlinLogging.logger { }

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler(){

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors
            .associate { it.field to (it.defaultMessage ?: "Invalid value") }
        kLogger.warn { "Validation failed: $errors" }

        return ResponseEntity.badRequest().body(
            mapOf(
                "error" to "Validation failed",
                "details" to errors
            )
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<Any> =
        ResponseEntity.status(404).body(mapOf("error" to ex.message))

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalState(ex: IllegalStateException): ResponseEntity<Any> =
        ResponseEntity.badRequest().body(mapOf("error" to ex.message))

    @ExceptionHandler(OptimisticLockingFailureException::class)
    fun handleOptimisticLocking(ex: OptimisticLockingFailureException): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.CONFLICT)
            .body(mapOf("error" to "Concurrent update detected. Please retry. ${ex.message}"))
}
