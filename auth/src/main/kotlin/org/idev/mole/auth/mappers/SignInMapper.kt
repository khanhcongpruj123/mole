package org.idev.mole.auth.mappers

import org.idev.mole.auth.dtos.SignInResponse
import org.idev.mole.auth.models.Token
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface SignInMapper {

    fun map(token: Token): SignInResponse
}