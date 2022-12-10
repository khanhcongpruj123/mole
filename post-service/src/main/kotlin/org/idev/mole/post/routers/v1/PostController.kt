package org.idev.mole.post.routers.v1

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/posts")
class PostController {

    @GetMapping
    fun posts() = ResponseEntity.ok(listOf("Post 1", "Post 2"))
}