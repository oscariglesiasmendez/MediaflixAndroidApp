package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Book
import com.mouredev.aristidevslogin.data.model.Client
import com.mouredev.aristidevslogin.data.model.Game
import com.mouredev.aristidevslogin.data.model.Movie
import com.mouredev.aristidevslogin.data.model.Order
import com.mouredev.aristidevslogin.data.model.Product
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
/*
    @GET("products")
    suspend fun listAllProducts(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ProductPage

*/

    @GET("products/all")
    suspend fun listAllProducts(
    ): List<Product>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Long
    ): Product

    @GET("books/all")
    suspend fun listAllBooks(
    ): List<Book>

    @GET("books/{id}")
    suspend fun getBookById(
        @Path("id") id: Long
    ): Book

    @GET("movies/all")
    suspend fun listAllMovies(
    ): List<Movie>

    @GET("movies/{id}")
    suspend fun getMovieById(
        @Path("id") id: Long
    ): Movie

    @GET("games/all")
    suspend fun listAllGames(
    ): List<Game>

    @GET("games/{id}")
    suspend fun getGameById(
        @Path("id") id: Long
    ): Game

    @POST("clients/add")
    suspend fun createClient(@Body clientDto: Client): Response<Client>

    @GET("clients/email")
    suspend fun searchClientByEmail(@Query("email") email: String): Response<Client>


    @GET("orders/client/{clientId}")
    suspend fun getAllOrdersByClient(@Path("clientId") id: Long): Response<List<Order>>


    companion object{
        //const val BASE_URL = "http://10.0.2.2:8080/api/"
        const val BASE_URL = "http://167.99.206.88:8080/api/"

        //Usar las llamadas para los que estan disponibles, no tiene sentido enseñar todos
    }

}