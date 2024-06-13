package com.mouredev.aristidevslogin.data.model

import java.sql.Date

data class Client(
    val clientId: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val creationDate: String, // Pongo el date como un string para parsearlo al formato admitido por la API (ISO)
    val available: Boolean
)