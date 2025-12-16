package com.example.medi.model

data class userModel(
    val userId:String="",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val dateOfBirth: String= "",
    val bloodType: String= "",
) {

    fun toMap(): Map<String,Any> {
        return mapOf(
            "userId" to userId,
            "name" to name,
            "email" to email,
            "password" to password,
            "dateOfBirth" to dateOfBirth,
            "bloodType" to bloodType
        )
    }
}
