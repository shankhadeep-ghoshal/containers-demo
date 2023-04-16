package com.shankhadeepghoshal.containersdemo.post

import com.fasterxml.jackson.annotation.JsonFormat
import com.shankhadeepghoshal.containersdemo.post.exception.ExternalServiceErringException
import com.shankhadeepghoshal.containersdemo.post.exception.PostNotFoundException
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
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

private val LOG = KotlinLogging.logger {}

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        LOG.error("Constraints violated", ex)
        val details = ex.fieldErrors.associate { it.field to it.defaultMessage }
        val validationErrorDetails = ValidationApiError(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST,
            "Invalid input",
            details
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorDetails)
    }

    @ExceptionHandler(PostNotFoundException::class)
    fun handleUserNotFound(ex: PostNotFoundException, webRequest: WebRequest): ResponseEntity<Any> {
        LOG.error("Post not found", ex)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.message)
    }

    @ExceptionHandler(ExternalServiceErringException::class)
    fun handleExternalServiceErringException(
        ex: ExternalServiceErringException,
        webRequest: WebRequest
    ): ResponseEntity<Any> {
        LOG.error("External service throwing error", ex)
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .header(HttpHeaders.RETRY_AFTER, "10")
            .body(ex.message)
    }

    @ExceptionHandler(Exception::class, kotlin.Exception::class)
    fun handleServerErrors(ex: Exception, webRequest: WebRequest): ResponseEntity<Any> {
        LOG.error("Exception occurred", ex)
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
