package com.shankhadeepghoshal.containersdemo.user

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

/**
 * @since 1.0
 * @author <a href="mailto:ghoshalshankhadeep@hotmail.com">Shankhadeep Ghoshal</a>
 **/

data class UserRequestDto(
    @field:NotNull(message = "Username can't be empty or blank")
    @field:NotEmpty(message = "Username can't be empty or blank")
    @field:NotBlank(message = "Username can't be empty or blank")
    val userName: String
)
