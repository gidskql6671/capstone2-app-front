package knu.dong.capstone2.dto

data class UserDto(
    val id: Long,
    val email: String,
    val rateLimit: Int,
    val currentUsedCount: Int,
    val isPremium: Boolean
)
