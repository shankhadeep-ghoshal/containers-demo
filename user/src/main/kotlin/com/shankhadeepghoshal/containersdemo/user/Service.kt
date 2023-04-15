package com.shankhadeepghoshal.containersdemo.user

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
        return userRepo.save(User(id = null, userName = userDto.userName))
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

    fun incrementPostCount(id: Int): User {
        val user = getUserByIdOrThrow(id)
        user.postCount = user.postCount + 1
        return userRepo.save(user)
    }

    private fun getUserByIdOrThrow(id: Int): User {
        return userRepo.findById(id).orElseThrow {
            UserNotFoundException("User with id $id not found")
        }
    }
}
