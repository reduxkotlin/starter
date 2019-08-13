package com.example.common.view

import com.example.common.models.Keyword
import com.example.common.state.AppState
import org.reduxkotlin.Dispatcher


data class MoviesListProps(
    val searchedMovies: List<Int>?,
    val searchedKeywords: List<Keyword>?,
    val searcherdPeoples: List<Int>?,
    val recentSearches: List<String>)


fun moviesListProps(searchText: String, isSearching: Boolean): PropsMapper<MoviesListProps> {
    fun map(state: AppState, dispatch: Dispatcher): MoviesListProps {
        val x = state.moviesState.search[searchText]
        if (isSearching) {
            return MoviesListProps(
                searchedMovies = x,
                searchedKeywords = state.moviesState.searchKeywords[searchText]?.take(5)?.map { it },
                searcherdPeoples = state.peoplesState.search[searchText],
                recentSearches = state.moviesState.recentSearches.map { it })
        }
        return MoviesListProps(
            searchedMovies = null,
            searchedKeywords = null,
            searcherdPeoples = null,
            recentSearches = listOf()
        )
    }
    return ::map
}

