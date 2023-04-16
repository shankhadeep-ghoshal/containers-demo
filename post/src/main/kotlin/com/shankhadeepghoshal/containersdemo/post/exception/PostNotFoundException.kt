package com.shankhadeepghoshal.containersdemo.post.exception

/**
 * @since 1.0
 * @author <a href="mailto:ghoshalshankhadeep@hotmail.com">Shankhadeep Ghoshal</a>
 **/

class PostNotFoundException(override val message: String) : RuntimeException(message)
