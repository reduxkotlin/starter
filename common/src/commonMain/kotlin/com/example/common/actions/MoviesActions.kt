package com.example.common.actions

import com.example.common.models.*
import com.example.common.services.APIService
import com.example.common.state.AppState
import com.example.common.state.MoviesMenu
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
data class MoviesActions {
    // MARK: - Requests

    fun FetchMoviesMenuList(list: MoviesMenu, page: Int): Thunk<AppState> =
        createThunk { dispatch, getState, extraArgument ->
            APIService.shared.GET<List<Movie>>(
                endpoint = list.endpoint(),
                params = mapOf("page", "${page}", "region" to AppUserDefaults.region)
            ) { result ->
                when (result) {
                    success -> dispatch(
                        SetMovieMenuList(
                            page = this.page,
                            list = this.list,
                            response = response
                        )
                    )
                    failure(error) -> {
                        print(error)
                        break
                    }
                }
            }
        }

    data class FetchDetail : AsyncAction(val movie: Int)
    {

        fun execute(state: FluxState?, dispatch: DispatchFunction) {
            APIService.shared.GET(endpoint = . movieDetail (movie =
                movie), params = mapOf<"append_to_response", "keywords,images", "include_image_language", "${Locale.current.languageCode ?: "en"},en,null">) { result ->
                when (result) {
                    let.success(response) -> dispatch(
                        SetDetail(
                            movie = this.movie,
                            response = response
                        )
                    )
                        .failure
                    -> break
                }
            }
        }
    }

    data class FetchRecommended : AsyncAction(val movie: Int)
    {

        fun execute(state: FluxState?, dispatch: DispatchFunction) {
            APIService.shared.GET(endpoint = . recommended (movie =
                movie), params = null) { result ->
                when (result) {
                    let.success(response) -> dispatch(
                        SetRecommended(
                            movie = this.movie,
                            response = response
                        )
                    )
                        .failure
                    -> break
                }
            }
        }
    }

    data class FetchSimilar : AsyncAction(val movie: Int)
    {

        fun execute(state: FluxState?, dispatch: DispatchFunction) {
            APIService.shared.GET(endpoint = . similar (movie = movie), params = null) { result ->
                when (result) {
                    let.success(response) -> dispatch(
                        SetSimilar(
                            movie = this.movie,
                            response = response
                        )
                    )
                        .failure
                    -> break
                }
            }
        }
    }

    data class FetchSearch : AsyncAction(
        val query: String,
    val page: Int)
    {

        fun execute(state: FluxState?, dispatch: DispatchFunction) {
            APIService.shared.GET(endpoint = . searchMovie, params = mapOf<"query", query, "page", "${page}">) { result ->
                when (result) {
                    let.success(response) -> dispatch(
                        SetSearch(
                            query = this.query,
                            page = this.page,
                            response = response
                        )
                    )
                        .failure
                    -> break
                }
            }
        }
    }

    data class FetchSearchKeyword : AsyncAction(val query: String)
    {

        fun execute(state: FluxState?, dispatch: DispatchFunction) {
            APIService.shared.GET(endpoint = . searchKeyword, params = mapOf<"query", query>) { result ->
                when (result) {
                    let.success(response) -> dispatch(
                        SetSearchKeyword(
                            query = this.query,
                            response = response
                        )
                    )
                        .failure
                    -> break
                }
            }
        }
    }

    data class FetchMoviesGenre : AsyncAction(
        val genre: Genre,
    val page: Int,
    val sortBy: MoviesSort)
    {

        fun execute(state: FluxState?, dispatch: DispatchFunction) {
            APIService.shared.GET(endpoint = . discover, params = mapOf<"with_genres", "${genre.id}", "page", "${page}", "sort_by", sortBy.sortByAPI()>) { result ->
                when (result) {
                    let.success(response) -> dispatch(
                        SetMovieForGenre(
                            genre = this.genre,
                            page = this.page,
                            response = response
                        )
                    )
                        .failure
                    -> break
                }
            }
        }
    }

    data class FetchMovieReviews : AsyncAction(val movie: Int)
    {

        fun execute(state: FluxState?, dispatch: DispatchFunction) {
            APIService.shared.GET(endpoint = . review (movie =
                movie), params = mapOf<"language", "en-US">) { result ->
                when (result) {
                    let.success(response) -> dispatch(
                        SetMovieReviews(
                            movie = this.movie,
                            response = response
                        )
                    )
                        .failure
                    -> break
                }
            }
        }
    }

    data class FetchMovieWithCrew : AsyncAction(val crew: Int)
    {

        fun execute(state: FluxState?, dispatch: DispatchFunction) {
            APIService.shared.GET(endpoint = . discover, params = mapOf<"with_people", "${crew}">) { result ->
                when (result) {
                    let.success(response) -> dispatch(
                        SetMovieWithCrew(
                            crew = this.crew,
                            response = response
                        )
                    )
                        .failure
                    -> break
                }
            }
        }
    }

    data class FetchMovieWithKeywords : AsyncAction(
        val keyword: Int,
    val page: Int)
    {

        fun execute(state: FluxState?, dispatch: DispatchFunction) {
            APIService.shared.GET(endpoint = . discover, params = mapOf<"page", "${page}", "with_keywords", "${keyword}">) { result ->
                when (result) {
                    let.success(response) -> dispatch(
                        SetMovieWithKeyword(
                            keyword = this.keyword,
                            page = this.page,
                            response = response
                        )
                    )
                        .failure
                    -> break
                }
            }
        }
    }

    data class FetchRandomDiscover : AsyncAction(var filter: DiscoverFilter? = null)
    {

        fun execute(state: FluxState?, dispatch: DispatchFunction) {
            var filter = this.filter
            if (filter == null) {
                filter = DiscoverFilter.randomFilter()
            }
            APIService.shared.GET(endpoint = . discover, params = filter!!.toParams()) { result ->
                when (result) {
                    let.success(response) -> dispatch(
                        SetRandomDiscover(
                            filter = filter!!,
                            response = response
                        )
                    )
                        .failure
                    -> break
                }
            }
        }
    }

    data class GenresResponse : Codable(val genres: List<Genre>)
    {}

    data class FetchGenres : AsyncAction {

        fun execute(state: FluxState?, dispatch: DispatchFunction) {
            APIService.shared.GET(endpoint = . genres, params = null) { result ->
                when (result) {
                    let.success(response) -> dispatch(SetGenres(genres = response.genres))
                        .failure
                    -> break
                }
            }
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
        val response: PaginatedResponse<Movie>)


    data class PushRandomDiscover(val movie: Int)

    class PopRandromDiscover

    data class SetMovieReviews(
        val movie: Int,
    val response: PaginatedResponse<Review>)

    data class AddCustomList(val list: CustomList)

    data class EditCustomList(
        val list: Int,
    val title: String?,
    val cover: Int?)

    data class AddMovieToCustomList(
        val list: Int,
    val movie: Int)

    data class AddMoviesToCustomList(
        val list: Int,
    val movies: List<Int>)

    data class RemoveMovieFromCustomList(
        val list: Int,
    val movie: Int)

    data class RemoveCustomList(val list: Int)

    data class SaveDiscoverFilter(val filter: DiscoverFilter)

    class ClearSavedDiscoverFilters
}
