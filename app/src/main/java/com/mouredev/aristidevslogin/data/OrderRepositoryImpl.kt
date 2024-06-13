package com.mouredev.aristidevslogin.data

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.mouredev.aristidevslogin.data.model.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class OrdersRepositoryImpl(
    private val api: Api
) : OrdersRepository {

    override suspend fun getAllOrdersByClient(clientId: Long): Flow<Result<List<Order>>> {
        return flow {
            try {
                val ordersFromApi = api.getAllOrdersByClient(clientId)
                if (ordersFromApi.isSuccessful) {
                    emit(Result.Success(ordersFromApi.body() ?: emptyList()))
                } else {
                    emit(Result.Error(message = "Error al obtener los pedidos"))
                }
            } catch (e: IOException) {
                emit(Result.Error(message = "Error cargando los pedidos: ${e.message}"))
            } catch (e: HttpException) {
                emit(Result.Error(message = "Error del servidor: ${e.message}"))
            } catch (e: Exception) {
                emit(Result.Error(message = "Error desconocido: ${e.message}"))
            }
        }
    }
}

/*
class OrdersRepositoryImpl(
    private val api: Api
) : OrdersRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getAllOrdersByClient(clientId: Long): Flow<Result<List<Order>>> {
        return flow {
            val ordersFromApi = try {
                api.getAllOrdersByClient(clientId)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error cargando los pedidos"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error del servidor"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error desconocido"))
                return@flow
            }

            if (ordersFromApi.isSuccessful) {
                emit(Result.Success(ordersFromApi.body() ?: emptyList()))
            } else {
                emit(Result.Error(message = "Error al obtener los pedidos"))
            }
        }
    }
}

 */