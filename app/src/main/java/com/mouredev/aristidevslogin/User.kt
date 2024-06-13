package com.mouredev.aristidevslogin

import java.util.Date

data class User(
    val id: String?,
    val userId: String,
    val firstName: String,
    val lastName: String,
    val creationDate : Date
) {

    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to this.userId,
            "first_name" to this.firstName,
            "last_name" to this.lastName,
            "creation_date" to this.creationDate
        )
    }

}