package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ProductsRepositoryImpl (
    private val api: Api
):ProductsRepository{

    override suspend fun getProductsList(): Flow<Result<List<Product>>> {
        return flow{
            val productsFromApi = try{

                api.listAllProducts(1,100)

            }catch(e: IOException){
                e.printStackTrace()
                emit(Result.Error(message="Error cargando los productos"))
                return@flow //Esto dirige el valor devuelto al flujo de valores emitidos    " https://www.youtube.com/watch?v=8IhNq0ng-wk "
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Result.Error(message="Error cargando los productos"))
                return@flow
            }catch (e: Exception){
                e.printStackTrace()
                emit(Result.Error(message="Error cargando los productos"))
                return@flow
            }

            emit(Result.Success(productsFromApi.products))

        }

    }

}