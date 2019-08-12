package com.example.common.view

import com.example.common.models.Movie
import com.example.common.models.People
import com.example.common.state.AppState
import org.reduxkotlin.Dispatcher

typealias PropsMapper =(AppState, Dispatcher) -> Props

data class Props(
    val movie: Movie,
    val characters: List<People>?,
    val credits: List<People>?,
    val recommended: List<Movie>?,
    val similar: List<Movie>?,
    val reviewsCount: Int?)



fun movieDetailMapProps(movieId: Int): PropsMapper {
    fun map(state: AppState, dispatch: Dispatcher): Props {
        var characters: List<People>? = null
        var credits: List<People>? = null
        var recommended: List<Movie>? = null
        var similar: List<Movie>? = null
        val peopleIds = state.peoplesState.peoplesMovies[movieId]
        if (peopleIds != null) {
            val cast = state.peoplesState.peoples.filter { it.value.character != null }
            characters = peopleIds.filter { cast[it] != null }.map { cast[it]!! }
            val departements = state.peoplesState.peoples.filter { it.value.department != null }
            credits = peopleIds.filter { departements[it] != null }.map { departements[it]!! }
            val movies = state.moviesState.movies
            val recommendedIds = state.moviesState.recommended[movieId]
            if (recommendedIds != null) {
                recommended = recommendedIds.filter { movies[it] != null }.map { movies[it]!! }
            }
            val simillarIds = state.moviesState.similar[movieId]
            if (simillarIds != null) {
                similar = simillarIds.filter { movies[it] != null }.map { movies[it]!! }
            }
        }
        return Props(
            movie = state.moviesState.movies[movieId]!!,
            characters = characters,
            credits = credits,
            recommended = recommended,
            similar = similar,
            reviewsCount = state.moviesState.reviews[movieId]?.size
        )
    }
    return ::map
}
