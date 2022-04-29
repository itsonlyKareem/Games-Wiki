package repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import network.ApiService
import network.models.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GamesRepository(private val apiService: ApiService) {
    private val gamesLiveData = MutableLiveData<MutableList<Game>>()

    var gameId: Int = 0
    lateinit var gameName: String
    lateinit var gameImage: String
    var gameRating: Int = 0
    var gameRatings: JSONArray = JSONArray()
    lateinit var rating: JSONObject
    lateinit var ratingTitle: String
    var ratingPercentage: Double = 0.0
    var gamePlayTime: Int = 0
    lateinit var gameLastUpdate: String
    lateinit var gameDominantColor: String
    var gamePlatforms: JSONArray = JSONArray()
    lateinit var platform: JSONObject
    lateinit var platformObject: JSONObject
    lateinit var platformName: String
    lateinit var platformReleaseDate: String
    var platformsList: MutableList<Platform> = arrayListOf()
    var gameStores: JSONArray = JSONArray()
    lateinit var store: JSONObject
    var storeId: Int = 0
    lateinit var storeObject: JSONObject
    lateinit var storeName: String
    lateinit var storeImage: String
    var storesList: MutableList<Store> = arrayListOf()
    var gameScreenshots: JSONArray = JSONArray()
    lateinit var gameScreenShot: JSONObject
    var screenShotsList: MutableList<ScreenShot> = arrayListOf()
    var gamesList: MutableList<Game> = arrayListOf()
    var ratingCount: Int = 0
    var ratingsListMega: MutableList<MutableList<Rating>> = arrayListOf()


    val games: LiveData<MutableList<Game>> = gamesLiveData

    fun getGames() {
        val result = apiService.fetchGames("7a22ee5b90074dea91bbf909c7a781a5")
        result.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful && response.body() != null) {
                    val mainObject = JSONObject(response.body()!!.string())
                    val gamesResponse = mainObject.getJSONArray("results")
                    for (i in 0 until gamesResponse.length()) {
                        val gameObject = JSONObject(gamesResponse.getJSONObject(i).toString())
                        val ratingsListMini: MutableList<Rating> = arrayListOf()

                        // ID
                        gameId = gameObject.getInt("id")
                        // Name
                        gameName = gameObject.getString("name")
                        // Background Image
                        gameImage = gameObject.getString("background_image")
                        // Overall Rating
                        gameRating = gameObject.getInt("rating")

                        // Ratings
                        gameRatings = gameObject.getJSONArray("ratings")
                        for (j in 0 until gameRatings.length()) {
                            rating = JSONObject(gameRatings.getJSONObject(j).toString())

                            ratingTitle = rating.getString("title")
                            ratingPercentage = rating.getDouble("percent")
                            ratingCount = rating.getInt("count")
                            ratingsListMini.add(Rating(ratingTitle, ratingPercentage, ratingCount))
                        }


                        // Playtime
                        gamePlayTime = gameObject.getInt("playtime")
                        // Last Updated
                        gameLastUpdate = gameObject.getString("updated")
                        // Overall Color
                        gameDominantColor = gameObject.getString("dominant_color")

                        // Platforms
                        gamePlatforms = gameObject.getJSONArray("platforms")
                        for (k in 0 until gamePlatforms.length()) {
                            platform = JSONObject(gamePlatforms.getJSONObject(k).toString())

                            platformObject =
                                JSONObject(platform.getJSONObject("platform").toString())

                            platformName = platformObject.getString("name")
                            platformReleaseDate = platform.getString("released_at")
                            platformsList.add(Platform(platformName, platformReleaseDate))

                        }

                        //Stores
                        gameStores = gameObject.getJSONArray("stores")
                        for (l in 0 until gameStores.length()) {
                            store = JSONObject(gameStores.optJSONObject(l).toString())
                            storeId = store.getInt("id")

                            storeObject = store.getJSONObject("store")
                            storeName = storeObject.getString("name")
                            storeImage = storeObject.getString("image_background")
                        }

                        // ScreenShots
                        gameScreenshots = gameObject.getJSONArray("short_screenshots")
                        for (m in 0 until gameScreenshots.length()) {
                            gameScreenShot = JSONObject(gameScreenshots.optJSONObject(m).toString())

                            screenShotsList.add(ScreenShot(gameScreenShot.getString("image")))
                        }



                        gamesList.add(Game(
                            id = gameId,
                            name = gameName,
                            image = gameImage,
                            overallRating = gameRating,
                            playtime = gamePlayTime,
                            lastUpdated = gameLastUpdate,
                            ratings = ratingsListMini,
                            platforms = platformsList,
                            stores = storesList,
                            screenshots = screenShotsList,
                            dominant_color = gameDominantColor
                        ))

                    }

                } else {

                }

                gamesLiveData.postValue(gamesList)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("Failed Response")
            }

        })


    }
}
