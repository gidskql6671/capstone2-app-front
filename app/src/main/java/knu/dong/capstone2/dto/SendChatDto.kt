package knu.dong.capstone2.dto

import kotlinx.serialization.Serializable

@Serializable
data class SendChatDto(val role: String, val message: String)
