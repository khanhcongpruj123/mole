package org.idev.mole.post

import org.idev.mole.post.services.KratosService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [PostApp::class])
class KratosServiceTest {

    @Autowired
    lateinit var kratosService: KratosService

    @Test
    fun `test who am i api`() {
        val res = kratosService.whoAmI("6RzOwY7sy8QE54PffXhKVveue7mLasIr")
        println(res)
    }
}