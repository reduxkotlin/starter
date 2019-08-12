package com.example.common.services

import com.example.common.getPreferredLanguage
import com.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


//
//  APIService.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 06/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
class APIService(
    val baseURL: String = "https://api.themoviedb.org/3",
    val apiKey: String = "1d9b898a212ea52e283351e521e17871"
) {
    companion object {
        val shared = APIService()
    }

    sealed class APIError : Error() {
        object noResponse : APIError()
        data class jsonDecodingError(val error: Error) : APIError()
        data class networkError(val error: Error) : APIError()
    }

    sealed class Endpoint {
        object popular : Endpoint()
        object topRated : Endpoint()
        object upcoming : Endpoint()
        object nowPlaying : Endpoint()
        object trending : Endpoint()
        data class movieDetail(val movie: Int) : Endpoint()
        data class recommended(val movie: Int) : Endpoint()
        data class similar(val movie: Int) : Endpoint()
        data class credits(val movie: Int) : Endpoint()
        data class review(val movie: Int) : Endpoint()
        object searchMovie : Endpoint()
        object searchKeyword : Endpoint()
        object searchPerson : Endpoint()
        object popularPersons : Endpoint()
        data class personDetail(val person: Int) : Endpoint()
        data class personMovieCredits(val person: Int) : Endpoint()
        data class personImages(val person: Int) : Endpoint()
        object genres : Endpoint()
        object discover : Endpoint()


        fun path(): String = when (this) {
            popular -> "movie/popular"
            is popularPersons -> "person/popular"
            is topRated -> "movie/top_rated"
            is upcoming -> "movie/upcoming"
            is nowPlaying -> "movie/now_playing"
            is trending -> "trending/movie/day"
            is movieDetail -> "movie/$this.movie}"
            is personDetail -> "person/$person}"
            is credits -> "movie/$movie/credits"
            is review -> "movie/$movie/reviews"
            is recommended -> "movie/$movie/recommendations"
            is similar -> "movie/$movie/similar"
            is personMovieCredits -> "person/$person/movie_credits"
            is personImages -> "person/$person/images"
            is searchMovie -> "search/movie"
            is searchKeyword -> "search/keyword"
            is searchPerson -> "search/person"
            is genres -> "genre/movie/list"
            is discover -> "discover/movie"
        }
    }

    val client by lazy {
        return@lazy try {
            HttpClient {
                install(JsonFeature) {
                    serializer = KotlinxSerializer(Json.nonstrict).apply {
                    }
                }
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
            }
        } catch (e: Exception) {
            throw RuntimeException("Error initializing: ${e.message}")
        }
    }

    inline fun <reified T> GET(
        endpoint: Endpoint,
        params: Map<String, String>?,
        crossinline completionHandler: (Result<T>).() -> Unit
    ) {
        GlobalScope.launch {
            Napier.d("BASE_URL = $baseURL")
            try {
                val response = client.get<T> {
                    apiUrl(endpoint.path())
                    parameter("api_key", apiKey)
                    parameter("language", getPreferredLanguage())
                    params?.forEach { parameter(it.key, it.value) }
                    Napier.d("URL: ${url.buildString()}")
                }

                completionHandler(Result.success(response))
            } catch (e: Exception) {
                completionHandler(Result.failure(e))
            }
        }
    }

    fun HttpRequestBuilder.apiUrl(path: String) {
        header(HttpHeaders.CacheControl, io.ktor.client.utils.CacheControl.MAX_AGE)
        url {
            takeFrom(baseURL)
            encodedPath = "/3/$path"
        }
    }
}
