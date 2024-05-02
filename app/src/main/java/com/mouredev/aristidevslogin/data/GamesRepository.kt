package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Game
import kotlinx.coroutines.flow.Flow

interface GamesRepository {

    suspend fun getGamesList():Flow<Result<List<Game>>>

}