package fragments

import adapters.GamesAdapter
import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.animation.AnimationUtils
import com.omega.gamestar.R
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import network.ApiClient
import network.ApiService
import repositories.GamesRepository
import viewmodels.GamesViewModel
import viewmodels.GamesViewModelFactory

class GamesFragment : Fragment() {

    lateinit var v: View
    lateinit var gamesCard: CardView
    lateinit var gamesRecycler: RecyclerView
    lateinit var gamesProgress: ProgressBar
    lateinit var nextPage: Button
    lateinit var previousPage: Button
    lateinit var gamesRepository: GamesRepository
    lateinit var adapter: GamesAdapter
    var page: Int = 1

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
        gamesRepository = GamesRepository(apiInterface)
        var gamesViewModelFactory = GamesViewModelFactory(gamesRepository,page)
        var gamesViewModel =
            ViewModelProvider(this, gamesViewModelFactory)[GamesViewModel::class.java]

        // RecyclerView Settings
        gamesRecycler.setHasFixedSize(true)
        gamesRecycler.alpha = 0f
        gamesRecycler.layoutManager = LinearLayoutManager(this.context)


        // Next Page
        nextPage.setOnClickListener {
            page++
            gamesRepository.getGames(page)
            gamesRecycler.scrollToPosition(0)
            gamesProgress.visibility = View.VISIBLE
        }

        // Previous Page
        previousPage.setOnClickListener {
            if (page == 1) {
                it.isEnabled = false
            } else {
                page--
                gamesRepository.getGames(page)
                gamesRecycler.scrollToPosition(0)
                gamesProgress.visibility = View.VISIBLE
            }
        }

        gamesViewModel.games.observeForever { it ->
            gamesProgress.visibility = View.GONE
            adapter = GamesAdapter(it)
            gamesRecycler.adapter = adapter
            gamesRecycler.itemAnimator = SlideInLeftAnimator(OvershootInterpolator(2.5f))
            gamesRecycler.animate().alphaBy(1f).setDuration(2000).start()
        }


        return v
    }

    private fun getViews(v: View?) {
        gamesCard = v!!.findViewById(R.id.gamesCard)
        gamesRecycler = v.findViewById(R.id.gamesRecycler)
        gamesProgress = v.findViewById(R.id.gamesProgress)
        nextPage = v.findViewById(R.id.nextPageGames)
        previousPage = v.findViewById(R.id.previousPageGames)
    }
}