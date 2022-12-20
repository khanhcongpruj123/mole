package org.idev.mole.post.models

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.MappedSuperclass


@MappedSuperclass
abstract class BaseModel {
    @Id
    @GeneratedValue
    var id: UUID? = null

    @CreatedDate
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null

    @Lob
    var metadata: ByteArray? = null
}