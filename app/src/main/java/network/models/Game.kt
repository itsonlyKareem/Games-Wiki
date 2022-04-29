package network.models

class Game(
    var id: Int,
    var name: String,
    var image: String,
    var overallRating: Int,
    var playtime: Int,
    var lastUpdated: String,
    var ratings: MutableList<Rating>,
    var platforms: MutableList<Platform>,
    var stores: MutableList<Store>,
    var screenshots: MutableList<ScreenShot>,
    var dominant_color: String
    ) {

}