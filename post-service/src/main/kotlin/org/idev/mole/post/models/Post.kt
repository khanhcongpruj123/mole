package org.idev.mole.post.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "tbl_post")
class Post(
    var userId: UUID? = null,
    @Column(nullable = false)
    var content: String? = null
) : BaseModel()
