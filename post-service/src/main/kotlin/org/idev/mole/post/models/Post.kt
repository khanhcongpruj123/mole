package org.idev.mole.post.models

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_post")
class Post(
    @Id
    @GeneratedValue
    val id: UUID? = null,
    var userId: UUID,
    var name: String? = null
) {
}
