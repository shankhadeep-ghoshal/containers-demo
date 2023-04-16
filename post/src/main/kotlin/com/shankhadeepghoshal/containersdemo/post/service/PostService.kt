package com.shankhadeepghoshal.containersdemo.post.service

import com.shankhadeepghoshal.containersdemo.post.Post
import com.shankhadeepghoshal.containersdemo.post.PostDto
import com.shankhadeepghoshal.containersdemo.post.Repository
import com.shankhadeepghoshal.containersdemo.post.exception.ExternalServiceErringException
import com.shankhadeepghoshal.containersdemo.post.exception.PostNotFoundException
import org.springframework.stereotype.Service

/**
 * @since 1.0
 * @author <a href="mailto:ghoshalshankhadeep@hotmail.com">Shankhadeep Ghoshal</a>
 **/

@Service
class PostService(
    private val repository: Repository,
    private val restService: RestService
) {

    fun createPost(postDto: PostDto): Post {
        val post = Post(id = null, authorId = postDto.authorId, text = postDto.text)
        if (restService.signalUserServiceIncreasePostCount(post.authorId)) {
            return repository.save(post)
        } else {
            throw ExternalServiceErringException("Internal Error. Please try again")
        }
    }

    fun getPost(postId: Int): Post {
        return getPostOrThrow(postId)
    }

    fun deletePost(postId: Int) {
        val post = getPostOrThrow(postId)
        if (restService.signalUserServiceDecreasePostCount(post.authorId)) {
            repository.delete(post)
        } else {
            throw ExternalServiceErringException("Internal Error. Please try again")
        }
    }

    fun editPost(id: Int, postDto: PostDto): Post {
        val post = getPostOrThrow(id)
        post.text = postDto.text
        return repository.save(post)
    }

    fun getPostOrThrow(postId: Int): Post {
        return repository.findById(postId).orElseThrow {
            PostNotFoundException(
                "Post with given id $postId not found"
            )
        }
    }
}
