package com.example.common.state

import com.example.common.services.APIService

data class AppState(
    val moviesState: MoviesState,
    val peoplesState: PeoplesState
)


enum class MoviesMenu(val title:String, val endpoint: APIService.Endpoint) {
    popular("Popular", APIService.Endpoint.popular),
    topRated("Top Rated", APIService.Endpoint.topRated),
    upcoming("Upcoming", APIService.Endpoint.upcoming),
    nowPlaying("Now Playing", APIService.Endpoint.nowPlaying),
    trending("Trending", APIService.Endpoint.trending),
    genres("Genres", APIService.Endpoint.genres);
}