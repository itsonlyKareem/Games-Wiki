package fragments

import adapters.GamesAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omega.gamestar.R
import network.ApiClient
import network.ApiService
import network.models.Game
import repositories.GamesRepository
import retrofit2.create
import viewmodels.GamesViewModel
import viewmodels.GamesViewModelFactory
import java.util.*

class GamesFragment : Fragment() {

    lateinit var v: View
    lateinit var gamesCard: CardView
    lateinit var gamesRecycler: RecyclerView
    lateinit var gamesProgress: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_games, container, false)
        getViews(v)

        // Main Card Settings
        gamesCard.setBackgroundResource(R.drawable.games_card)

        // Receiving and modelling response
        val apiInterface = ApiClient.getInstance().create(ApiService::class.java)
        val gamesRepository = GamesRepository(apiInterface)
        val gamesViewModelFactory = GamesViewModelFactory(gamesRepository)
        val gamesViewModel = ViewModelProvider(this,gamesViewModelFactory).get(GamesViewModel::class.java)

        // RecyclerView Settings
        gamesRecycler.setHasFixedSize(true)
        gamesRecycler.alpha = 0f
        gamesRecycler.layoutManager = LinearLayoutManager(this.context)

        gamesViewModel.games.observe(viewLifecycleOwner) { it ->
            gamesProgress.visibility = View.GONE
            val adapter = GamesAdapter(it)
            gamesRecycler.adapter = adapter
            gamesRecycler.animate().alphaBy(1f).setDuration(2000).start()
        }

        return v
    }

    private fun getViews(v: View?) {
        gamesCard = v!!.findViewById(R.id.gamesCard)
        gamesRecycler = v.findViewById(R.id.gamesRecycler)
        gamesProgress = v.findViewById(R.id.gamesProgress)
    }
}