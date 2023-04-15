package com.shankhadeepghoshal.containersdemo.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

/**
 * @since 1.0
 * @author <a href="mailto:ghoshalshankhadeep@hotmail.com">Shankhadeep Ghoshal</a>
 **/

@Entity
data class User(
    @Id @GeneratedValue var id: Int?,
    @Column(name = "user_name", unique = true) var userName: String,
    @Column(name = "post_count") var postCount: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as User
        return (this.id == other.id) && (this.userName == other.userName)
    }

    override fun hashCode(): Int {
        var result = (id?.hashCode() ?: 0)
        result = 31 * result + userName.hashCode()
        result = 31 * result + postCount.hashCode()

        return result
    }
}
