package adapters

import android.animation.ObjectAnimator
import android.os.Looper
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Scroller
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.omega.gamestar.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import network.models.Game
import java.time.Duration
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.Handler
import kotlin.concurrent.schedule

class GamesAdapter(private val gamesList: MutableList<Game>) :
    RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = gamesList[position]
        var platforms: String = ""
        var totalPercentage = 0.0
        var totalCount = 0
        var average = 0.0

        holder.image.load(game.image)
        holder.name.text = game.name
        holder.date.text = game.lastUpdated.substring(0,10)

        game.platforms.forEach {
            platforms += it.name + "\n"
        }
        holder.platforms.text = platforms
        for (i in 0 until game.ratings.size) {
            totalCount += game.ratings[i].count
            totalPercentage += game.ratings[i].percent

        }

        average = totalCount/totalPercentage
        average = Math.round(average*10.0)/10.0
        holder.rating.text = average.toString() + "%"

        var handler: android.os.Handler = android.os.Handler(Looper.getMainLooper())
        holder.itemView.alpha = 0f
        holder.itemView.translationX = -200f
        GlobalScope.launch (Dispatchers.Main){
            holder.itemView.animate().apply {
                translationXBy(200f)
                alpha(1f)
                duration = 600
                interpolator = interpolator
            }.start()
        }
    }

    override fun getItemCount(): Int {
        return gamesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById<TextView>(R.id.gameList_name)
        val date: TextView = itemView.findViewById<TextView>(R.id.gameList_date)
        val image: ImageView = itemView.findViewById<ImageView>(R.id.gameList_image)
        val platforms: TextView = itemView.findViewById<TextView>(R.id.gamesList_platforms)
        val rating: TextView = itemView.findViewById<TextView>(R.id.gamesList_rating)
    }

}
