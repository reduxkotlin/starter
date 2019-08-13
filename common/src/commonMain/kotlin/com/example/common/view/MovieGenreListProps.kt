package com.example.common.view

import com.example.common.state.AppState
import org.reduxkotlin.Dispatcher

data class MovieGenreListProps(
    val movies: List<Int>,
    val dispatcher: Dispatcher
) {

    fun props(genreId: Int): PropsMapper<MovieGenreListProps> {
        fun map(state: AppState, dispatcher: Dispatcher): MovieGenreListProps {
            return MovieGenreListProps(movies = state.moviesState.withGenreId(genreId),
                dispatcher = dispatcher)
        }
        return ::map
    }
}

fun movieGenreProps(genreId: Int): PropsMapper<MovieGenreListProps> {
    fun map(state: AppState, dispatcher: Dispatcher): MovieGenreListProps {
        return MovieGenreListProps(movies = state.moviesState.withGenreId(genreId),
            dispatcher = dispatcher)
    }
    return ::map
}