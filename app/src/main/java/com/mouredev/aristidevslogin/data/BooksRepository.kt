package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Book
import com.mouredev.aristidevslogin.data.model.Product
import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    suspend fun getBooksList():Flow<Result<List<Book>>>

    suspend fun getBookById(bookId: Long): Flow<Result<Book>>

}