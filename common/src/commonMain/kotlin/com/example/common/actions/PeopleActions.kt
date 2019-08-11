package com.example.common.actions

import com.example.common.models.*
import com.example.common.services.APIService
import com.example.common.services.APIService.*
import com.example.common.services.APIService.Endpoint.*
import org.reduxkotlin.createThunk
import kotlin.Result.Companion.success


//
//  CastsAction.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 09/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
class PeopleActions {

    fun FetchDetail(people: Int) = createThunk { dispatch, getState, extraArgument ->
            APIService.shared.GET(endpoint = personDetail(person = people), params = null) { result  ->
                when (result) {
                    success -> dispatch(SetDetail(person = result.response))
                        .failure -> break
                }
            }
        }

    data class ImagesResponse(
        val id: Int,
    val profiles: List<ImageData>)

    fun FetchImages(people: Int) = createThunk { dispatch, getState, extraArgument ->
            APIService.shared.GET(endpoint = .personImages(person = people), params = null) { result  ->
                when (result) {
                    let .success(response) -> dispatch(SetImages(people = this.people, images = response.profiles))
                        .failure -> break
                }
            }
        }

    data class PeopleCreditsResponse(
        val cast: List<Movie>?,
    val crew: List<Movie>?)

    fun FetchPeopleCredits(people: Int) = createThunk { dispatch, getState, extraArgument ->
            APIService.shared.GET(endpoint = .personMovieCredits(person = people), params = null) { result  ->
                when (result) {
                    let .success(response) -> dispatch(SetPeopleCredits(people = this.people, response = response))
                        .failure -> break
                }
            }
        }

    fun fetchMovieCasts(movie: Int) = createThunk { dispatch, getState, extraArgument ->
            APIService.shared.GET(endpoint = .credits(movie = movie), params = null) { result  ->
                when (result) {
                    let .success(response) -> store.dispatch(action = SetMovieCasts(movie = movie, response = response))
                        .failure -> break
                }
            }
        }

    fun FetchSearch(query: String, page: Int) = createThunk { dispatch, getState, extraArgument ->
            APIService.shared.GET(endpoint = .searchPerson, params = mapOf<"query" , query, "page" , "${page}">) { result  ->
                when (result) {
                    let .success(response) -> dispatch(SetSearch(query = this.query, page = this.page, response = response))
                        .failure -> break
                }
            }
        }

    fun fetchPopular(page: Int) = createThunk { dispatch, getState, extraArgument ->
            APIService.shared.GET(endpoint = .popularPersons, params = mapOf<"page" , "${page}", "region" , AppUserDefaults.region>) { result  ->
                when (result) {
                    let .success(response) -> dispatch(SetPopular(page = this.page, response = response))
                    let .failure(error) -> {
                        print(error)
                        break
                    }
                }
            }
        }

    data class SetDetail(val person: People)

    data class SetImages(
        val people: Int,
    val images: List<ImageData>)

    data class SetMovieCasts(
        val movie: Int,
    val response: CastResponse
    )

    data class SetSearch(
        val query: String,
    val page: Int,
    val response: PaginatedResponse<People>
    )

    data class SetPopular(
        val page: Int,
    val response: PaginatedResponse<People>
    )

    data class SetPeopleCredits(
        val people: Int,
    val response: PeopleCreditsResponse)

    data class AddToFanClub(val people: Int)

    data class RemoveFromFanClub(val people: Int)
}
