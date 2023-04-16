package com.shankhadeepghoshal.containersdemo.user

import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * @since 1.0
 * @author <a href="mailto:ghoshalshankhadeep@hotmail.com">Shankhadeep Ghoshal</a>
 **/
private val kLogger = KotlinLogging.logger {}

@RestController
@RequestMapping("/")
@CrossOrigin("*")
@Validated
class Controller(private val service: Service) {

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createUser(@Valid @RequestBody userRequestDto: UserRequestDto): ResponseEntity<User> {
        kLogger.info { "Request received for username $userRequestDto for creation" }
        return ResponseEntity.ok(service.createUser(userRequestDto))
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserById(@PathVariable("id") id: Int): ResponseEntity<User> {
        kLogger.info { "Request received for id $id for fetching user details" }
        return ResponseEntity.ok(service.getUserById(id))
    }

    @PutMapping(
        "/{id}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateUser(
        @PathVariable("id") id: Int,
        @Valid @RequestBody userRequestDto: UserRequestDto
    ):
            ResponseEntity<User> {
        kLogger.info { "Request received for username $userRequestDto for update for id $id" }
        return ResponseEntity.ok(service.updateUser(id, userRequestDto))
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") id: Int): ResponseEntity<Any> {
        kLogger.info { "Request received user id $id for deletion" }
        service.deleteUserById(id)
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    @PutMapping("/{id}/increase")
    fun increasePostCount(@PathVariable("id") id: Int): ResponseEntity<Any> {
        kLogger.info { "Request received user id $id for post count increment" }
        return ResponseEntity.ok(service.increasePostCount(id))
    }

    @PutMapping("/{id}/decrease")
    fun decreasePostCount(@PathVariable("id") id: Int): ResponseEntity<Any> {
        kLogger.info { "Request received user id $id for post count decrement" }
        return ResponseEntity.ok(service.decreasePostCount(id))
    }
}
