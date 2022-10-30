package org.idev.mole.auth.dto

data class ServerInfo(
    val name: String,
    val version: String,
    val status: ServerStatus
)

enum class ServerStatus {
    GOOD
}