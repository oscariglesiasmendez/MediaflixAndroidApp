package com.mouredev.aristidevslogin.ui.principal.screens.bottombar_screens.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mouredev.aristidevslogin.data.GamesRepository
import com.mouredev.aristidevslogin.data.Result
import com.mouredev.aristidevslogin.data.model.Book
import com.mouredev.aristidevslogin.data.model.Game
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GamesViewModel(
    private val gamesRepository: GamesRepository
) : ViewModel() {


    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games = _games.asStateFlow()

    private val _game = MutableStateFlow<Result<Game>?>(null)
    val game = _game.asStateFlow()

    //Mensajes visual de error
    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            gamesRepository.getGamesList().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }

                    is Result.Success -> {
                        //Cambiamos el state de nuestros libros para que haga recompose
                        //Me aseguro de que no es nulo antes de iterar
                        result.data?.let { games ->
                            _games.update { games }
                        }
                    }
                }
            }
        }
    }

    fun loadGame(gameId: Long) {
        viewModelScope.launch {
            val gameResult = gamesRepository.getGameById(gameId)
                .first()
            _game.value = gameResult
        }
    }
}