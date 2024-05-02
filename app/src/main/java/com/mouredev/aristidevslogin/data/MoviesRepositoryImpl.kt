package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MoviesRepositoryImpl(
    private val api: Api
) : MoviesRepository {

    override suspend fun getMoviesList(): Flow<Result<List<Movie>>> {
        return flow {
            val moviesFromApi = try {

                api.listAllMovies()

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error cargando las peliculas"))
                return@flow //Esto dirige el valor devuelto al flujo de valores emitidos    " https://www.youtube.com/watch?v=8IhNq0ng-wk "
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error cargando las peliculas"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error cargando las peliculas"))
                return@flow
            }

            emit(Result.Success(moviesFromApi))
        }

    }

}