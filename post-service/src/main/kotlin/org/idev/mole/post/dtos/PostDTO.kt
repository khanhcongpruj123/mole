package org.idev.mole.post.dtos

import java.io.Serializable

data class PostDTO(
    var userId: String? = null,
    var name: String? = null
) : Serializable
