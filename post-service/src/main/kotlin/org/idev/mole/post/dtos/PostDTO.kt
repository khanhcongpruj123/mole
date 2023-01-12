package org.idev.mole.post.dtos

import jakarta.validation.constraints.NotNull
import java.io.Serializable

data class PostDTO(
    var id: String? = null,
    var userId: String? = null,
    @NotNull
    val content: String? = null,
) : Serializable
