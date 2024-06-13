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

                //api.listAllProducts(1,100)
                api.listAllProducts()

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

            //emit(Result.Success(productsFromApi.products))
            emit(Result.Success(productsFromApi))

        }

    }


    override suspend fun getProductById(productId: Long): Flow<Result<Product>> {
        return flow {
            val productFromApi = try {
                api.getProductById(productId)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error fetching product"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                if (e.code() == 404) {
                    emit(Result.Error(message = "Product not found"))
                } else {
                    emit(Result.Error(message = "Error fetching product"))
                }
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error fetching product"))
                return@flow
            }

            emit(Result.Success(productFromApi))
        }
    }

}