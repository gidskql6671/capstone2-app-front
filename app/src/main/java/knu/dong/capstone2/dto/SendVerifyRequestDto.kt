package knu.dong.capstone2.dto

import kotlinx.serialization.Serializable

@Serializable()
data class SendVerifyRequestDto(val email: String)