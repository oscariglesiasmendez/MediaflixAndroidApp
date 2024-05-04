package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Book
import com.mouredev.aristidevslogin.data.model.Game
import com.mouredev.aristidevslogin.data.model.Movie
import com.mouredev.aristidevslogin.data.model.Product
import com.mouredev.aristidevslogin.data.model.ProductPage
import retrofit2.http.GET
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




    @GET("books/all")
    suspend fun listAllBooks(
    ): List<Book>

    @GET("movies/all")
    suspend fun listAllMovies(
    ): List<Movie>

    @GET("games/all")
    suspend fun listAllGames(
    ): List<Game>


    companion object{
        //const val BASE_URL = "http://192.168.1.140:8080/api/"
        const val BASE_URL = "http://192.168.0.16:8080/api/"
    }

}