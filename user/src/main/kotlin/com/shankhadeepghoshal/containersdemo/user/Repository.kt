package com.shankhadeepghoshal.containersdemo.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @since 1.0
 * @author <a href="mailto:ghoshalshankhadeep@hotmail.com">Shankhadeep Ghoshal</a>
 **/

@Repository
interface Repository: JpaRepository<User, Int>
