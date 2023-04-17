package com.shankhadeepghoshal.containersdemo.post

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull


/**
 * @since 1.0
 * @author <a href="mailto:ghoshalshankhadeep@hotmail.com">Shankhadeep Ghoshal</a>
 **/

data class PostDto(
    @field:NotNull @field:Min(1) val authorId: Int,
    @field:NotNull val text: String
)
