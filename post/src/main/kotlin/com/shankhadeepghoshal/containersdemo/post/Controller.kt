package com.shankhadeepghoshal.containersdemo.post

import com.shankhadeepghoshal.containersdemo.post.service.PostService
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
@Validated
class Controller(private val postService: PostService) {

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getPost(@PathVariable("id") id: Int): ResponseEntity<Any?> {
        kLogger.info { "Request to get post with id $id received" }
        return ResponseEntity.ok(postService.getPost(id))
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createPost(@Valid @RequestBody postDto: PostDto): ResponseEntity<Any?> {
        kLogger.info { "Request to create post by author with id ${postDto.authorId} received" }
        return ResponseEntity.ok(postService.createPost(postDto))
    }

    @PutMapping(
        "/{id}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun editPost(
        @PathVariable("id") id: Int,
        @Valid @RequestBody postDto: PostDto
    ): ResponseEntity<Any?> {
        kLogger.info {
            "Request to modify post with id $id by author with id ${postDto.authorId} received"
        }
        return ResponseEntity.ok(postService.editPost(id, postDto))
    }

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable("id") id: Int): ResponseEntity<Any?> {
        kLogger.info { "Request to delete post with id $id received" }
        postService.deletePost(id)
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}
