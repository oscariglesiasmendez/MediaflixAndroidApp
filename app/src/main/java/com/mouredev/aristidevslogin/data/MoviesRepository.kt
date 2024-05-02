package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMoviesList():Flow<Result<List<Movie>>>

}