package org.idev.mole.post.controllers.v1

import jakarta.validation.Valid
import org.idev.mole.post.dtos.PostDTO
import org.idev.mole.post.mappers.PostMapper
import org.idev.mole.post.services.PostService
import org.json.JSONObject
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    val postService: PostService,
    val postMapper: PostMapper
) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun posts(@AuthenticationPrincipal session: JSONObject): ResponseEntity<List<PostDTO>> {
        val userId = session.getJSONObject("identity")
            .getString("id")
        return ResponseEntity.ok(postService.postRepository.findByUserId(UUID.fromString(userId)).map { postMapper.map(it) })
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPost(
        @AuthenticationPrincipal
        session: JSONObject,
        @Valid
        @RequestBody
        postDTO: PostDTO
    ): ResponseEntity<Any> {
        val userId = session.getJSONObject("identity")
            .getString("id")
        postDTO.userId = userId
        postService.sendCreatePostMessage(postDTO)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updatePost(@PathVariable("id") id: String, @RequestBody postDTO: PostDTO): ResponseEntity<Any> {
        postDTO.id = id
        postService.updatePost(postDTO);
        return ResponseEntity.accepted().build()
    }
}