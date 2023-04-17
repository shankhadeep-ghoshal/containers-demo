package com.shankhadeepghoshal.containersdemo.post

import jakarta.persistence.*
import java.time.LocalDate

/**
 * @since 1.0
 * @author <a href="mailto:ghoshalshankhadeep@hotmail.com">Shankhadeep Ghoshal</a>
 **/

@Entity
data class Post(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) val id: Int?,
    @Column(name = "author_id") val authorId: Int,
    var text: String,
    @Column(name = "posted_at") val postedAt: LocalDate = LocalDate.now()
)
