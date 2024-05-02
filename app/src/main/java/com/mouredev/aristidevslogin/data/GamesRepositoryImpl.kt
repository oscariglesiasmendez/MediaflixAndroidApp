package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Game
import com.mouredev.aristidevslogin.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GamesRepositoryImpl(
    private val api: Api
) : GamesRepository {

    override suspend fun getGamesList(): Flow<Result<List<Game>>> {
        return flow {
            val gamesFromApi = try {

                api.listAllGames()

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error cargando los juegos"))
                return@flow //Esto dirige el valor devuelto al flujo de valores emitidos    " https://www.youtube.com/watch?v=8IhNq0ng-wk "
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error cargando los juegos"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error cargando los juegos"))
                return@flow
            }

            emit(Result.Success(gamesFromApi))
        }

    }

}