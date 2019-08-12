package com.example.common.actions

import com.example.common.models.*
import com.example.common.services.APIService
import com.example.common.services.APIService.Endpoint.*
import com.example.common.thunk
import com.github.aakira.napier.Napier

//
//  CastsAction.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 09/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
class PeopleActions {

    fun fetchDetail(people: Int) = thunk { dispatch, _, _ ->
        APIService.shared.GET<People>(
            endpoint = personDetail(person = people),
            params = null
        ) {
            onSuccess { dispatch(SetDetail(person = it)) }
            onFailure { }
        }
    }

    data class ImagesResponse(
        val id: Int,
        val profiles: List<ImageData>
    )

    fun fetchImages(people: Int) = thunk { dispatch, _, _ ->
        APIService.shared.GET<ImagesResponse>(
            endpoint = personImages(person = people),
            params = null
        ) {
            onSuccess { dispatch(SetImages(people = people, images = it.profiles)) }
            onFailure {}
        }
    }

    data class PeopleCreditsResponse(
        val cast: List<Movie>?,
        val crew: List<Movie>?
    )

    fun fetchPeopleCredits(people: Int) = thunk { dispatch, _, _ ->
        APIService.shared.GET<PeopleCreditsResponse>(
            endpoint = personMovieCredits(person = people),
            params = null
        ) {
            onSuccess { dispatch(SetPeopleCredits(people = people, response = it)) }
            onFailure { }
        }
    }

    fun fetchMovieCasts(movie: Int) = thunk { dispatch, _, _ ->
        APIService.shared.GET<CastResponse>(endpoint = credits(movie = movie), params = null) {
            onSuccess { dispatch(SetMovieCasts(movie = movie, response = it)) }
            onFailure { }
        }
    }

    fun fetchSearch(query: String, page: Int) = thunk { dispatch, _, _ ->
        APIService.shared.GET<PaginatedResponse<People>>(
            endpoint = searchPerson,
            params = mapOf("query" to query, "page" to page.toString())
        ) {
            onSuccess { dispatch(SetSearch(query = query, page = page, response = it)) }
            onFailure {}
        }
    }

    fun fetchPopular(page: Int) = thunk { dispatch, getState, extraArgument ->
        APIService.shared.GET<PaginatedResponse<People>>(
            endpoint = popularPersons,
            params = mapOf("page" to "$page", "region" to "us")
        ) {
            onSuccess { dispatch(SetPopular(page = page, response = it)) }
            onFailure { Napier.d(it.message ?: "error") }
        }
    }


    data class SetDetail(val person: People)

    data class SetImages(
        val people: Int,
        val images: List<ImageData>
    )

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
        val response: PeopleCreditsResponse
    )

    data class AddToFanClub(val people: Int)

    data class RemoveFromFanClub(val people: Int)
}
