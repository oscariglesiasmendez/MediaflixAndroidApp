package com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mouredev.aristidevslogin.data.MoviesRepository
import com.mouredev.aristidevslogin.data.Result
import com.mouredev.aristidevslogin.data.model.Movie
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {


    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    //Mensajes visual de error
    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            moviesRepository.getMoviesList().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }

                    is Result.Success -> {
                        //Cambiamos el state de nuestras peliculas para que haga recompose
                        //Me aseguro de que no es nulo antes de iterar
                        result.data?.let { movies ->
                            _movies.update { movies }
                        }
                    }
                }
            }
        }
    }
}