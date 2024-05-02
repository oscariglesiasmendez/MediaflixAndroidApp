package com.mouredev.aristidevslogin.data

import com.mouredev.aristidevslogin.data.model.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    suspend fun getBooksList():Flow<Result<List<Book>>>

}