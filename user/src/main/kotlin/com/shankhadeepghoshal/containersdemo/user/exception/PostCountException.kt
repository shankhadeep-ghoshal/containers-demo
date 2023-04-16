package com.shankhadeepghoshal.containersdemo.user.exception

/**
 * @since 1.0
 * @author <a href="mailto:ghoshalshankhadeep@hotmail.com">Shankhadeep Ghoshal</a>
 **/

class PostCountException(override val message: String): RuntimeException(message)
