package com.shankhadeepghoshal.containersdemo.post

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @since 1.0
 * @author <a href="mailto:ghoshalshankhadeep@hotmail.com">Shankhadeep Ghoshal</a>
 **/

@RestController
@RequestMapping("/")
class Controller {
    @GetMapping
    fun greetings() = "Hello Posts"
}
