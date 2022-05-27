package viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import repositories.GamesRepository

class GamesViewModelFactory(private val gamesRepository: GamesRepository, private val page: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GamesViewModel(gamesRepository,page) as T
    }
}