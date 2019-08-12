package com.example.common.actions

import com.example.common.MoviesSort
import com.example.common.models.*
import com.example.common.services.APIService
import com.example.common.services.APIService.*
import com.example.common.state.AppState
import com.example.common.state.MoviesMenu
import com.example.common.thunk
import com.github.aakira.napier.Napier
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.reduxkotlin.Thunk
import org.reduxkotlin.createThunk
import kotlin.Result.Companion.failure


//
//  MoviesAction.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 06/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
class MoviesActions {
    // MARK: - Requests

    fun fetchMoviesMenuList(list: MoviesMenu, page: Int) = thunk { dispatch, _, _ ->
        APIService.shared.GET<PaginatedResponse<Movie>>(
            endpoint = list.endpoint,
            params = mapOf(
                "page" to page.toString(),
                "region" to "us"
            ) //AppUserDefaults.region)
        ) {
            onSuccess {
                dispatch(
                    SetMovieMenuList(
                        page = page,
                        list = list,
                        response = it
                    )
                )
            }
            onFailure {
                Napier.d(it.message ?: "Error fetching")
            }
        }
    }

    fun fetchDetail(movie: Int) =
        thunk { dispatch, _, _ ->
            APIService.shared.GET<Movie>(
                endpoint = Endpoint.movieDetail(movie = movie),
                params = mapOf(
                    "append_to_response" to "keywords,images", "include_image_language" to
                            /*"${Locale.current.languageCode ?: */"en,en,null"
                )
            ) {
                onSuccess { dispatch(SetDetail(movie = movie, response = it)) }
                onFailure { }
            }
        }

    fun fetchRecommended(movie: Int) = thunk { dispatch, getState, extraArgument ->
        APIService.shared.GET<PaginatedResponse<Movie>>(
            endpoint = Endpoint.recommended(
                movie = movie
            ), params = null
        ) {
            onSuccess { dispatch(SetRecommended(movie = movie, response = it)) }
            onFailure { }
        }
    }

    fun fetchSimilar(movie: Int) = thunk { dispatch, _, _ ->
        APIService.shared.GET<PaginatedResponse<Movie>>(
            endpoint = Endpoint.similar(movie = movie),
            params = null
        ) {
            onSuccess { dispatch(SetSimilar(movie = movie, response = it)) }
            onFailure { }
        }
    }

    fun fetchSearch(query: String, page: Int) = thunk { dispatch, _, _ ->
        APIService.shared.GET<PaginatedResponse<Movie>>(
            endpoint = Endpoint.searchMovie,
            params = mapOf("query" to query, "page" to "${page}")
        ) {
            onSuccess {
                dispatch(
                    SetSearch(
                        query = query,
                        page = page,
                        response = it
                    )
                )
            }
            onFailure { }
        }
    }

    fun fetchSearchKeyword(query: String) = thunk { dispatch, _, _ ->
        APIService.shared.GET<PaginatedResponse<Keyword>>(
            endpoint = Endpoint.searchKeyword,
            params = mapOf("query" to query)
        ) {
            onSuccess {
                dispatch(
                    SetSearchKeyword(
                        query = query,
                        response = it
                    )
                )
            }
            onFailure { }
        }
    }

    fun fetchMoviesGenre(genre: Genre, page: Int, sortBy: MoviesSort) = thunk { dispatch, _, _ ->
        APIService.shared.GET<PaginatedResponse<Movie>>(
            endpoint = Endpoint.discover,
            params = mapOf(
                "with_genres" to "${genre.id}",
                "page" to "$page",
                "sort_by" to sortBy.sortByAPI
            )
        ) {
            onSuccess {
                dispatch(
                    SetMovieForGenre(
                        genre = genre,
                        page = page,
                        response = it
                    )
                )
            }
            onFailure { }
        }
    }

    fun fetchMovieReviews(movie: Int) = thunk { dispatch, _, _ ->
        APIService.shared.GET<PaginatedResponse<Review>>(
            endpoint = Endpoint.review(
                movie =
                movie
            ), params = mapOf("language" to "en-US")
        ) {
            onSuccess { dispatch(SetMovieReviews(movie = movie, response = it)) }
            onFailure { }
        }
    }

    fun fetchMovieWithCrew(crew: Int) = thunk { dispatch, _, _ ->
        APIService.shared.GET<PaginatedResponse<Movie>>(
            endpoint = Endpoint.discover,
            params = mapOf("with_people" to "$crew")
        ) {
            onSuccess { dispatch(SetMovieWithCrew(crew = crew, response = it)) }
            onFailure { }
        }
    }

    fun fetchMovieWithKeywords(keyword: Int, page: Int) = thunk { dispatch, _, _ ->
        APIService.shared.GET<PaginatedResponse<Movie>>(
            endpoint = Endpoint.discover, params = mapOf(
                "page" to "$page",
                "with_keywords" to "$keyword"
            )
        ) {
            onSuccess {
                dispatch(
                    SetMovieWithKeyword(
                        keyword = keyword,
                        page = page,
                        response = it
                    )
                )
            }
            onFailure { }
        }
    }

    fun fetchRandomDiscover(filter: DiscoverFilter? = null) = thunk { dispatch, _, _ ->
        var filter = filter
        if (filter == null) {
            filter = DiscoverFilter.randomFilter()
        }
        APIService.shared.GET<PaginatedResponse<Movie>>(
            endpoint = Endpoint.discover,
            params = filter.toParams()
        ) {
            onSuccess {
                dispatch(
                    SetRandomDiscover(filter = filter, response = it)
                )
            }
            onFailure { }
        }
    }

    @Serializable
    data class GenresResponse(val genres: List<Genre>)

    fun fetchGenres() = thunk { dispatch, _, _ ->
        APIService.shared.GET<GenresResponse>(endpoint = Endpoint.genres, params = null) {
            onSuccess { dispatch(SetGenres(genres = it.genres)) }
            onFailure { }
        }
    }

    data class SetMovieMenuList(
        val page: Int,
        val list: MoviesMenu,
        val response: PaginatedResponse<Movie>
    )

    data class SetDetail(
        val movie: Int,
        val response: Movie
    )

    data class SetRecommended(
        val movie: Int,
        val response: PaginatedResponse<Movie>
    )


    data class SetSimilar(
        val movie: Int,
        val response: PaginatedResponse<Movie>
    )


    data class KeywordResponse(
        val id: Int,
        val keywords: List<Keyword>
    )


    data class SetSearch(
        val query: String,
        val page: Int,
        val response: PaginatedResponse<Movie>
    )


    data class SetGenres(val genres: List<Genre>)


    data class SetSearchKeyword(
        val query: String,
        val response: PaginatedResponse<Keyword>
    )


    data class AddToWishlist(val movie: Int)


    data class RemoveFromWishlist(val movie: Int)

    data class AddToSeenList(val movie: Int)

    data class RemoveFromSeenList(val movie: Int)

    data class SetMovieForGenre(
        val genre: Genre,
        val page: Int,
        val response: PaginatedResponse<Movie>
    )

    data class SetMovieWithCrew(
        val crew: Int,
        val response: PaginatedResponse<Movie>
    )

    data class SetMovieWithKeyword(
        val keyword: Int,
        val page: Int,
        val response: PaginatedResponse<Movie>
    )

    class ResetRandomDiscover

    data class SetRandomDiscover(
        val filter: DiscoverFilter,
        val response: PaginatedResponse<Movie>
    )


    data class PushRandomDiscover(val movie: Int)

    class PopRandromDiscover

    data class SetMovieReviews(
        val movie: Int,
        val response: PaginatedResponse<Review>
    )

    data class AddCustomList(val list: CustomList)

    data class EditCustomList(
        val list: Int,
        val title: String?,
        val cover: Int?
    )

    data class AddMovieToCustomList(
        val list: Int,
        val movie: Int
    )

    data class AddMoviesToCustomList(
        val list: Int,
        val movies: List<Int>
    )

    data class RemoveMovieFromCustomList(
        val list: Int,
        val movie: Int
    )

    data class RemoveCustomList(val list: Int)

    data class SaveDiscoverFilter(val filter: DiscoverFilter)

    class ClearSavedDiscoverFilters
}
