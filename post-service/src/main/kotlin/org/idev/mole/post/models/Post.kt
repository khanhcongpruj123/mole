package org.idev.mole.post.models

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_post")
class Post(
    var userId: UUID,
    @Column(nullable = false)
    var content: String
) : BaseModel()
