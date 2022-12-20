package org.idev.mole.post.dtos

import java.io.Serializable
import javax.validation.constraints.NotNull

data class PostDTO(
    var userId: String? = null,
    @NotNull
    val content: String? = null,
) : Serializable
