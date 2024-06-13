package com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mouredev.aristidevslogin.data.BooksRepository
import com.mouredev.aristidevslogin.data.Result
import com.mouredev.aristidevslogin.data.model.Book
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {


    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books = _books.asStateFlow()

    private val _book = MutableStateFlow<Result<Book>?>(null)
    val book = _book.asStateFlow()

    //Mensajes visual de error
    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            booksRepository.getBooksList().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }

                    is Result.Success -> {
                        //Cambiamos el state de nuestros libros para que haga recompose
                        //Me aseguro de que no es nulo antes de iterar
                        result.data?.let { books ->
                            _books.update { books }
                        }
                    }
                }
            }
        }
    }

    fun loadBook(bookId: Long) {
        viewModelScope.launch {
            val bookResult = booksRepository.getBookById(bookId)
                .first()
            _book.value = bookResult
        }
    }
}