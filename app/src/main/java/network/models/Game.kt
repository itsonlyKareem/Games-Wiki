package network.models

class Game(
    id: Int,
    name: String,
    image: String,
    overallRating: Int,
    playtime: Int,
    lastUpdated: String,
    ratings: List<Rating>,
    platforms: List<Platform>
    ) {
}