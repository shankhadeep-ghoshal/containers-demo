package com.shankhadeepghoshal.containersdemo.user

import com.shankhadeepghoshal.containersdemo.user.exception.PostCountException
import com.shankhadeepghoshal.containersdemo.user.exception.UserNotFoundException
import com.shankhadeepghoshal.containersdemo.user.exception.UsernameExistsException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

/**
 * @since 1.0
 * @author <a href="mailto:ghoshalshankhadeep@hotmail.com">Shankhadeep Ghoshal</a>
 **/

@Service
@Transactional
class Service(private val userRepo: Repository) {

    fun createUser(userDto: UserRequestDto): User {
        val tempUser = userRepo.getUserByUserName(userDto.userName)
        if (null == tempUser) {
            return userRepo.save(User(id = null, userName = userDto.userName))
        } else {
            throw UsernameExistsException("User with username ${userDto.userName} already exists")
        }
    }

    fun getUserById(id: Int): User {
        return getUserByIdOrThrow(id)
    }

    fun updateUser(id: Int, userDto: UserRequestDto): User {
        val user = getUserByIdOrThrow(id)
        if (userDto.userName == user.userName) {
            return user
        }
        user.userName = userDto.userName
        return userRepo.save(user)
    }

    fun deleteUserById(id: Int) {
        userRepo.delete(getUserByIdOrThrow(id))
    }

    fun increasePostCount(id: Int): User {
        val user = getUserByIdOrThrow(id)
        return modifyPostCount(user, PostModificationState.INCREASE)
    }

    fun decreasePostCount(id: Int): User {
        val user = getUserByIdOrThrow(id)
        if (user.postCount < 1) {
            throw PostCountException("Post count is currently 0. Cannot decrease")
        } else {
            return modifyPostCount(user, PostModificationState.DECREASE)
        }
    }

    private fun modifyPostCount(user: User, postModificationState: PostModificationState): User {
        when (postModificationState) {
            PostModificationState.INCREASE -> user.postCount + 1
            PostModificationState.DECREASE -> user.postCount - 1
        }

        return userRepo.save(user)
    }

    private fun getUserByIdOrThrow(id: Int): User {
        return userRepo.findById(id).orElseThrow {
            UserNotFoundException("User with id $id not found")
        }
    }

    private enum class PostModificationState {
        INCREASE,
        DECREASE
    }
}
