package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Client
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ClientService {

    @POST("clients/add")
    suspend fun createClient(@Body clientDto: Client): Response<Client>

    @GET("clients/email/{email}")
    suspend fun searchClientByEmail(@Path("email") email: String): Response<Client>

}