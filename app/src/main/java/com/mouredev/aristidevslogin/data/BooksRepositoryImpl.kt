package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Book
import com.mouredev.aristidevslogin.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class BooksRepositoryImpl(
    private val api: Api
) : BooksRepository {

    override suspend fun getBooksList(): Flow<Result<List<Book>>> {
        return flow {
            val booksFromApi = try {

                api.listAllBooks()

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error cargando los libros"))
                return@flow //Esto dirige el valor devuelto al flujo de valores emitidos    " https://www.youtube.com/watch?v=8IhNq0ng-wk "
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error cargando los libros"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error cargando los libros"))
                return@flow
            }

            emit(Result.Success(booksFromApi))
        }

    }


    override suspend fun getBookById(bookId: Long): Flow<Result<Book>> {
        return flow {
            val bookFromApi = try {
                api.getBookById(bookId)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = "Error fetching book"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                if (e.code() == 404) {
                    emit(Result.Error(message = "Book not found"))
                } else {
                    emit(Result.Error(message = "Error fetching book"))
                }
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(message = "Error fetching book"))
                return@flow
            }

            emit(Result.Success(bookFromApi))
        }
    }

}