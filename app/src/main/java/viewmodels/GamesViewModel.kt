package viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import network.models.Game
import repositories.GamesRepository

class GamesViewModel(private val gamesRepository: GamesRepository): ViewModel() {
    init {
        viewModelScope.launch (Dispatchers.IO) {
            gamesRepository.getGames()
        }
    }

    val games: LiveData<MutableList<Game>> = gamesRepository.games
}