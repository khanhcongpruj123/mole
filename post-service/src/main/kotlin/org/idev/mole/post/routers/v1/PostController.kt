package org.idev.mole.post.routers.v1

import org.idev.mole.post.dtos.PostDTO
import org.idev.mole.post.mappers.PostMapper
import org.idev.mole.post.repositories.PostRepository
import org.json.JSONObject
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    val postRepository: PostRepository,
    val postMapper: PostMapper
) {

    @GetMapping
    fun posts(@AuthenticationPrincipal session: JSONObject): ResponseEntity<List<PostDTO>> {
        val userId = session.getJSONObject("identity")
            .getString("id")
        return ResponseEntity.ok(postRepository.findByUserId(UUID.fromString(userId)).map { postMapper.map(it) })
    }

    @PostMapping
    fun createPost(@AuthenticationPrincipal session: JSONObject, @RequestBody postDTO: PostDTO): ResponseEntity<Any> {
        val userId = session.getJSONObject("identity")
            .getString("id")
        postDTO.userId = userId
        postRepository.save(postMapper.map(postDTO))
        return ResponseEntity.ok().build()
    }
}