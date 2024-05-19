package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMoviesList():Flow<Result<List<Movie>>>

    suspend fun getMovieById(movieId: Long): Flow<Result<Movie>>

}