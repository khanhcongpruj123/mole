package org.idev.mole.auth

import org.idev.mole.auth.services.AuthServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [AuthApp::class])
class AuthServiceTest {

    @Autowired
    private lateinit var authService: AuthServiceImpl

    @Test
    fun `test sign up`() {
        authService.signUp("test2", "123qweA!", "khanhcongpruj_test2@gmail.com")
    }
}