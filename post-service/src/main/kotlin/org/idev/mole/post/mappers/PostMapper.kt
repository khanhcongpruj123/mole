package org.idev.mole.post.mappers

import org.idev.mole.post.dtos.PostDTO
import org.idev.mole.post.models.Post
import org.mapstruct.Mapper

@Mapper
interface PostMapper {

    fun map(dto: PostDTO): Post

    fun map(post: Post): PostDTO
}