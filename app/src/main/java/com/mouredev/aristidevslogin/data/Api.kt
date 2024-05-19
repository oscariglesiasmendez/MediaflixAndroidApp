package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Book
import com.mouredev.aristidevslogin.data.model.Game
import com.mouredev.aristidevslogin.data.model.Movie
import com.mouredev.aristidevslogin.data.model.Product
import retrofit2.http.GET
import retrofit2.http.Path

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


    companion object{
        const val BASE_URL = "http://192.168.1.141:8080/api/"
        //const val BASE_URL = "http://192.168.0.16:8080/api/"

        //Usar las llamadas para los que estan disponibles, no tiene sentido enseñar todos
    }

}