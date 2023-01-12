package org.idev.mole.post.services

import mu.KLogger
import org.idev.mole.post.dtos.PostDTO
import org.idev.mole.post.mappers.PostMapper
import org.idev.mole.post.repositories.PostRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PostService(
    val postRepository: PostRepository,
    val rabbitTemplate: RabbitTemplate,
    val postMapper: PostMapper,
    val logger: KLogger
) {

    /**
     * send create post request to message broker
     * */
    fun sendCreatePostMessage(postDTO: PostDTO) {
        logger.info("Send create post message {}", postDTO)
        rabbitTemplate.convertAndSend("mole.create-post-exchange", "mole.post.create", postDTO)
    }

    /**
     * save post to persistence
     * */
    fun createPost(postDTO: PostDTO) {
        logger.info("Create post {}", postDTO)
        postRepository.save(postMapper.map(postDTO))
    }

    /**
     * Update content post
     * */
    @Transactional
    fun updatePost(postDTO: PostDTO) {
        val post = postRepository.findById(UUID.fromString(postDTO.id))
            .orElseThrow()
        post.content = postDTO.content
        // transactional will save your change
    }
}