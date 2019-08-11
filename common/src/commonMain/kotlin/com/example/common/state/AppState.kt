package com.example.common.state

data class AppState(
    val moviesState: MoviesState,
    val peoplesState: PeoplesState
)


enum class MoviesMenu {
    popular,
    topRated,
    upcoming,
    nowPlaying,
    trending,
    genres

    fun title() = when (this) {
        popular -> "Popular"
        topRated -> "Top Rated"
        upcoming -> "Upcoming"
        nowPlaying -> "Now Playing"
        trending -> "Trending"
        genres -> "Genres"
    }

    fun endpoint() {
        //todo
    }
}