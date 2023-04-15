package com.shankhadeepghoshal.containersdemo.user

import com.fasterxml.jackson.annotation.JsonFormat
import mu.KotlinLogging
import org.springframework.http.*
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime

/**
 * @since 1.0
 * @author <a href="mailto:ghoshalshankhadeep@hotmail.com">Shankhadeep Ghoshal</a>
 **/

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        logger.error("Constraints violated", ex)
        val details = ex.fieldErrors.associate { it.field to it.defaultMessage }
        val validationErrorDetails = ValidationApiError(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST,
            "Invalid input",
            details
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorDetails)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(ex: UserNotFoundException, webRequest: WebRequest): ResponseEntity<Any> {
        logger.error("User not found", ex)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
    }

    @ExceptionHandler(Exception::class, kotlin.Exception::class)
    fun handleServerErrors(ex: Exception, webRequest: WebRequest): ResponseEntity<Any> {
        logger.error("Exception occurred", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong")
    }

    data class ValidationApiError(
        @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss"
        ) val timestamp: LocalDateTime,
        val status: HttpStatus,
        val message: String,
        val fieldsErrors: Map<String, String?>
    )
}
