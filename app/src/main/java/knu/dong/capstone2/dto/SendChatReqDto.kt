package knu.dong.capstone2.dto

import kotlinx.serialization.Serializable

@Serializable
data class SendChatReqDto(val id: String, val message: String)
