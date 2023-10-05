package knu.dong.capstone2.dto

import kotlinx.serialization.Serializable

@Serializable
data class SendChatUsingSocketReqDto(
    val chatbotId: Long,
    val userId: Long,
    val message: String
)
