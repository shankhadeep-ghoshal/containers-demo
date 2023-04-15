package com.shankhadeepghoshal.containersdemo.user

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

/**
 * @since 1.0
 * @author [Shankhadeep Ghoshal](mailto:ghoshalshankhadeep@hotmail.com)
 */

@ExtendWith(MockitoExtension::class)
class ServiceTest {

    @Mock
    lateinit var repository: Repository

    @InjectMocks
    lateinit var service: Service

    @Test
    fun createUser_WithValidUsername() {
        val userName = "Balu"
        val userExpected = User(id = 1, userName = userName, postCount = 0)
        val userToSupply = User(userName = userName, id = null)

        Mockito.`when`(repository.save(userToSupply)).thenReturn(userExpected)
        val userReturned = service.createUser(UserRequestDto(userName))

        Assertions.assertEquals(userExpected, userReturned)
        Mockito.verify(repository, Mockito.times(1)).save(userToSupply)
    }

    @Test
    fun getUserById_WhenUserExists() {
        val id = 1
        val userExpected = User(id = id, userName = "Balu", postCount = 0)

        Mockito.`when`(repository.findById(id)).thenReturn(Optional.ofNullable(userExpected))
        val userReturned = service.getUserById(id)

        Assertions.assertEquals(userExpected, userReturned)
        Mockito.verify(repository, Mockito.times(1)).findById(id)
    }

    @Test
    fun updateUser_WithUserPreset_WithValidUsername() {
        val id = 1
        val newUserName = "Balu2"
        val userSupplied = User(id = id, userName = "Balu")
        val userExpected = User(id = id, userName = newUserName)

        Mockito.`when`(repository.findById(id)).thenReturn(Optional.ofNullable(userSupplied))
        Mockito.`when`(repository.save(userExpected)).thenReturn(userExpected)
        val userReturned = service.updateUser(id, UserRequestDto(newUserName))

        Assertions.assertEquals(userExpected, userReturned)
        Mockito.verify(repository, Mockito.times(1)).findById(id)
        Mockito.verify(repository, Mockito.times(1)).save(userExpected)
    }

    @Test
    fun deleteUserById_WithUserPresent() {
        val id = 1
        val userToDelete = User(id = id, userName = "Balu")

        Mockito.`when`(repository.findById(id)).thenReturn(Optional.ofNullable(userToDelete))
        Mockito.doNothing().`when`(repository).delete(userToDelete)
        service.deleteUserById(id)
        Mockito.`when`(repository.findById(id)).thenReturn(Optional.empty())

        Assertions.assertThrows(UserNotFoundException::class.java) { service.getUserById(id) }
        Mockito.verify(repository, Mockito.times(1)).delete(userToDelete)
        Mockito.verify(repository, Mockito.times(2)).findById(id)
    }

    @Test
    fun incrementPostCount_WithUserPresent() {
        val id = 1
        val userExpectedPreIncrement = User(id = id, userName = "Balu")
        val userExpectedPostIncrement = User(id = id, userName = "Balu", postCount = 1)

        Mockito.`when`(repository.findById(id))
            .thenReturn(Optional.ofNullable(userExpectedPreIncrement))
        Mockito.`when`(repository.save(userExpectedPreIncrement))
            .thenReturn(userExpectedPostIncrement)
        val userAfterPostCountIncreased = service.incrementPostCount(id)

        Assertions.assertEquals(1, userAfterPostCountIncreased.postCount)
        Mockito.verify(repository, Mockito.times(1)).findById(id)
        Mockito.verify(repository, Mockito.times(1)).save(userAfterPostCountIncreased)
    }
}
