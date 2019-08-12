package com.example.common

import com.example.common.state.AppState

enum class MoviesSort(val title: String, val sortByAPI: String) {
    byReleaseDate("by release date", "release_date.asc"),
    byAddedDate("by added date", "primary_release_dat.desc"),
    byScore("by rating", "vote_average.desc"),
    byPopularity("by populartiy", "popularity.desc")
}

//FIXME: @SwiftKotlin - Kotlin does not support where clauses in extensions:  where Iterator.Element == Int

/*
fun Sequence.sortedMoviesIds(by: MoviesSort, state: AppState) : List<Int> {
    when (by) {
            .byAddedDate -> {
        val metas = state.moviesState.moviesUserMeta.filter { this.contains(it.key) }
        return metas.sorted { it.value.addedToList ?: Date() > $1.value.addedToList ?: Date() }.compactMap { it.key }
    }
            .byReleaseDate -> {
        val movies = state.moviesState.movies.filter { this.contains(it.key) }
        return movies.sorted { it.value.releaseDate ?: Date() > $1.value.releaseDate ?: Date() }.compactMap { it.key }
    }
            .byPopularity -> {
        val movies = state.moviesState.movies.filter { this.contains(it.key) }
        return movies.sorted { it.value.popularity > $1.value.popularity }.compactMap { it.key }
    }
            .byScore -> {
        val movies = state.moviesState.movies.filter { this.contains(it.key) }
        return movies.sorted { it.value.vote_average > $1.value.vote_average }.compactMap { it.key }
    }
    }
}

 */
