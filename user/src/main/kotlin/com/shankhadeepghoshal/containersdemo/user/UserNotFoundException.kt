package com.shankhadeepghoshal.containersdemo.user

/**
 * @since 1.0
 * @author <a href="mailto:ghoshalshankhadeep@hotmail.com">Shankhadeep Ghoshal</a>
 **/

class UserNotFoundException(override val message: String) : RuntimeException(message)
