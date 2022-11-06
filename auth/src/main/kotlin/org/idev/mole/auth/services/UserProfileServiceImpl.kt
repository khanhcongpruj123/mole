package org.idev.mole.auth.services

import org.idev.mole.auth.models.UserProfile
import org.keycloak.admin.client.resource.RealmResource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class UserProfileServiceImpl(@Qualifier("moleRealmResource") private val moleRealmResource: RealmResource) : UserProfileService {

    override fun getUserProfile(userId: String): UserProfile {
        // get user from keycloak server by id
        val user = moleRealmResource.users().get(userId)
        // get infor from user and return user profile
        return user.toRepresentation().let {
            UserProfile(it.firstName, it.lastName)
        }
    }
}