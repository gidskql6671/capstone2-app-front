package knu.dong.capstone2.dto

import kotlinx.serialization.Serializable

@Serializable()
data class LoginResponseDto(val id: Long, val email: String)
