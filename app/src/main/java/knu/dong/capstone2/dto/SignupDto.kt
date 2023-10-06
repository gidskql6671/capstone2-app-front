package knu.dong.capstone2.dto

import kotlinx.serialization.Serializable

@Serializable
data class SignupDto(val email: String, val verifyCode: Int, val password: String)
