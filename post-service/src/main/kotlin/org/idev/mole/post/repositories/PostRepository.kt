package org.idev.mole.post.repositories

import org.idev.mole.post.models.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface PostRepository : JpaRepository<Post, UUID> {

    @Query("SELECT p FROM Post p WHERE p.userId = :userId")
    fun findByUserId(@Param("userId") userId: UUID): List<Post>

}