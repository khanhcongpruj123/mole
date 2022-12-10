package org.idev.mole.post.services

import org.json.JSONObject

interface KratosService {

    fun whoAmI(bearerToken: String): JSONObject?
}