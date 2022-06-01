package adapters

import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.load
import com.omega.gamestar.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import network.models.Game


class GamesAdapter(private val gamesList: MutableList<Game>) :
    RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = gamesList[position]
        var platforms = ""
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

        holder.itemView.alpha = 0f
        holder.itemView.translationX = -200f
        GlobalScope.launch (Dispatchers.Main){
            holder.itemView.animate().apply {
                translationXBy(200f)
                alpha(1f)
                duration = 600
                interpolator = interpolator
            }.start()
            delay(1000)
        }.start()

        GlobalScope.launch {
            val bitmap: Bitmap = Picasso.get().load(game.image).get()
            Palette.from(bitmap).generate {
                if (it != null) {
                    holder.itemView.setBackgroundColor(it.getDominantColor(0x00FFFFFF))
                }
            }
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
