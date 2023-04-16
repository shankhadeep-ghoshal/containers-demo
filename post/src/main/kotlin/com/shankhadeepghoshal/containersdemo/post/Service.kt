package com.shankhadeepghoshal.containersdemo.post

import org.springframework.stereotype.Service

/**
 * @since 1.0
 * @author <a href="mailto:ghoshalshankhadeep@hotmail.com">Shankhadeep Ghoshal</a>
 **/

@Service
class Service(private val repository: Repository) {

    fun createPost(postDto: PostDto): Post {
        val post = Post(id = null, authorId = postDto.authorId, text = postDto.text)
        return repository.save(post)
    }

    fun getPost(postId: Int): Post {
        return getPostOrThrow(postId)
    }

    fun deletePost(postId: Int) {
        repository.delete(getPostOrThrow(postId))
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
