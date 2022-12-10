package org.idev.mole.post.services

interface KratosService {

    fun whoAmI(bearerToken: String): Map<*, *>?
}