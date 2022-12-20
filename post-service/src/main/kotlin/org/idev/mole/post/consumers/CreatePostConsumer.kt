package org.idev.mole.post.consumers

import mu.KLogger
import org.idev.mole.post.dtos.PostDTO
import org.idev.mole.post.services.PostService
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class CreatePostConsumer(
    val postService: PostService,
    val logger: KLogger
) {

    /**
     * Create post queue, exchange and binding
     * */
    @Bean
    fun createPostQueue() = QueueBuilder.durable("mole.create-post-queue")
        .withArgument("x-dead-letter-exchange", "mole.create-post-exchange.failure")
        .withArgument("x-dead-letter-routing-key", "mole.post.create.failure")
        .build()

    @Bean
    fun createPostExchange() = ExchangeBuilder.topicExchange("mole.create-post-exchange")
        .build<TopicExchange>()

    @Bean
    fun createPostBinding(
        @Qualifier("createPostQueue")
        queue: Queue,
        @Qualifier("createPostExchange")
        exchange: TopicExchange
    ) = BindingBuilder.bind(queue)
        .to(exchange)
        .with("mole.post.create")

    /**
     * Failure create post queue, exchange and binding
     * */
    @Bean
    fun failureCreatePostQueue() = QueueBuilder.durable("mole.create-post-queue.failure")
        .build()

    @Bean
    fun failureCreatePostExchange() = ExchangeBuilder.directExchange("mole.create-post-exchange.failure")
        .build<DirectExchange>()

    @Bean
    fun failureCreatePostBinding(
        @Qualifier("failureCreatePostQueue")
        queue: Queue,
        @Qualifier("failureCreatePostExchange")
        exchange: DirectExchange
    ) = BindingBuilder.bind(queue)
        .to(exchange)
        .with("mole.post.create.failure")

    /**
     * Create post message listener
     * */
    @RabbitListener(queues = ["mole.create-post-queue"])
    fun createPostListener(postDTO: PostDTO) {
        logger.info("Receive create post request {}", postDTO)
        postService.createPost(postDTO)
    }
}