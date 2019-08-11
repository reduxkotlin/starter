package com.example.common.reducers

import com.example.common.state.MoviesState


//
//  MoviesStateReducer.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 06/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
fun moviesStateReducer(state: MoviesState, action: Action) : MoviesState {
    var state = state
    when (action) {
        is MoviesActions.SetMovieMenuList -> {
            if (action.page == 1) {
                state.moviesList[action.list] = action.response.results.map { it.id }
            } else {
                var list = state.moviesList[action.list]
                if (list != null) {
                    list.append(contentsOf = action.response.results.map { it.id })
                    state.moviesList[action.list] = list
                } else {
                    state.moviesList[action.list] = action.response.results.map { it.id }
                }
            }
            state.movies += action.response.results
        }
        let action as MoviesActions.SetDetail -> state.movies[action.movie] = action.response
        let action as MoviesActions.SetRecommended -> {
            state.recommended[action.movie] = action.response.results.map { it.id }
            state = mergeMovies(movies = action.response.results, state = state)
        }
        let action as MoviesActions.SetSimilar -> {
            state.similar[action.movie] = action.response.results.map { it.id }
            state = mergeMovies(movies = action.response.results, state = state)
        }
        let action as MoviesActions.SetSearch -> {
            if (action.page == 1) {
                state.search[action.query] = action.response.results.map { it.id }
            } else {
                state.search[action.query]?.append(contentsOf = action.response.results.map { it.id })
            }
            state = mergeMovies(movies = action.response.results, state = state)
        }
        let action as MoviesActions.SetSearchKeyword -> state.searchKeywords[action.query] = action.response.results
        let action as MoviesActions.AddToWishlist -> {
            state.wishlist.insert(action.movie)
            state.seenlist.remove(action.movie)
            var meta = state.moviesUserMeta[action.movie] ?: MovieUserMeta()
            meta.addedToList = Date()
            state.moviesUserMeta[action.movie] = meta
        }
        let action as MoviesActions.RemoveFromWishlist -> state.wishlist.remove(action.movie)
        let action as MoviesActions.AddToSeenList -> {
            state.seenlist.insert(action.movie)
            state.wishlist.remove(action.movie)
            var meta = state.moviesUserMeta[action.movie] ?: MovieUserMeta()
            meta.addedToList = Date()
            state.moviesUserMeta[action.movie] = meta
        }
        let action as MoviesActions.RemoveFromSeenList -> state.seenlist.remove(action.movie)
        let action as MoviesActions.AddMovieToCustomList -> state.customLists[action.list]?.movies?.insert(action.movie)
        let action as MoviesActions.AddMoviesToCustomList -> {
            var list = state.customLists[action.list]
            if (list != null) {
                for (movie in action.movies) {
                    list.movies.insert(movie)
                }
                state.customLists[action.list] = list
            }
        }
        let action as MoviesActions.RemoveMovieFromCustomList -> state.customLists[action.list]?.movies?.remove(action.movie)
        let action as MoviesActions.SetMovieForGenre -> {
            if (action.page == 1) {
                state.withGenre[action.genre.id] = action.response.results.map { it.id }
            } else {
                state.withGenre[action.genre.id]?.append(contentsOf = action.response.results.map { it.id })
            }
            state = mergeMovies(movies = action.response.results, state = state)
        }
        let action as MoviesActions.SetRandomDiscover -> {
            if (state.discover.isEmpty()) {
                state.discover = action.response.results.map { it.id }
            } else if (state.discover.size < 10) {
                state.discover.insert(contentsOf = action.response.results.map { it.id }, at = 0)
            }
            state = mergeMovies(movies = action.response.results, state = state)
            state.discoverFilter = action.filter
        }
        let action as MoviesActions.SetMovieReviews -> state.reviews[action.movie] = action.response.results
        let action as MoviesActions.SetMovieWithCrew -> {
            state.withCrew[action.crew] = action.response.results.map { it.id }
            state = mergeMovies(movies = action.response.results, state = state)
        }
        let action as MoviesActions.SetMovieWithKeyword -> {
            if (action.page == 1) {
                state.withKeywords[action.keyword] = action.response.results.map { it.id }
            } else {
                state.withKeywords[action.keyword]?.append(contentsOf = action.response.results.map { it.id })
            }
            state = mergeMovies(movies = action.response.results, state = state)
        }
        let action as MoviesActions.AddCustomList -> state.customLists[action.list.id] = action.list
        let action as MoviesActions.EditCustomList -> {
            var list = state.customLists[action.list]
            if (list != null) {
                val cover = action.cover
                if (cover != null) {
                    list.cover = cover
                }
                val title = action.title
                if (title != null) {
                    list.name = title
                }
                state.customLists[action.list] = list
            }
        }
        let action as MoviesActions.RemoveCustomList -> state.customLists[action.list] = null
        _ as MoviesActions.PopRandromDiscover -> state.discover.popLast()
        let action as MoviesActions.PushRandomDiscover -> state.discover.append(action.movie)
        _ as MoviesActions.ResetRandomDiscover -> {
            state.discoverFilter = null
            state.discover = listOf()
        }
        let action as MoviesActions.SetGenres -> {
            state.genres = action.genres
            state.genres.insert(Genre(id = -1, name = "Random"), at = 0)
        }
        let action as PeopleActions.SetPeopleCredits -> {
            val crews = action.response.crew
            if (crews != null) {
                state = mergeMovies(movies = crews, state = state)
            }
            val casts = action.response.cast
            if (casts != null) {
                state = mergeMovies(movies = casts, state = state)
            }
        }
        let action as MoviesActions.SaveDiscoverFilter -> state.savedDiscoverFilters.append(action.filter)
        _ as MoviesActions.ClearSavedDiscoverFilters -> state.savedDiscoverFilters = listOf()
        else -> break
    }
    return state
}

fun +=(lhs: inout Map<Int, Movie>, rhs: List<Movie>) {
    for (movie in rhs) {
        lhs[movie.id] = movie
    }
}

private fun mergeMovies(movies: List<Movie>, state: MoviesState) : MoviesState {
    var state = state
    for (movie in movies) {
        if (state.movies[movie.id] == null) {
            state.movies[movie.id] = movie
        }
    }
    return state
}
