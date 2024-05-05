package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Book
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


    override suspend fun getGameById(gameId: Long): Flow<Result<Game>> {
        return flow {
            val gameFromApi = try {
                api.getGameById(gameId)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error fetching game"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                if (e.code() == 404) {
                    emit(Result.Error(message = "Game not found"))
                } else {
                    emit(Result.Error(message = "Error fetching game"))
                }
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error fetching game"))
                return@flow
            }

            emit(Result.Success(gameFromApi))
        }
    }

}