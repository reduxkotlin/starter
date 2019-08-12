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
        object toRated : Endpoint()
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
            is toRated -> "movie/top_rated"
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

    suspend inline fun <reified T> GET(
        endpoint: APIService.Endpoint,
        params: Map<String, String>?,
        completionHandler: (Result<T>) -> Unit
    ) {


//        Napier.d("URL: $url")
        /*
        val queryURL = baseURL.appendingPathComponent(endpoint.path())
        val components = URLComponents(url = queryURL, resolvingAgainstBaseURL = true)!!
        components.queryItems = listOf(
            URLQueryItem(name = "api_key", value = apiKey),
            URLQueryItem(name = "language", value = Locale.preferredLanguages[0])
        )

        val params = params
        if (params != null) {
            for ((_, value) in params.enumerated()) {
                components.queryItems?.append(URLQueryItem(name = value.key, value = value.value))
            }
        }
         */


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

        /*
        val request = URLRequest(url = components.url!!)
        request.httpMethod = "GET"
        val task = URLSession.shared.dataTask(with = request) { data, response, error ->
            val data = data
            if (data == null) {
                DispatchQueue.main.async { completionHandler(. failure (.noResponse)) }
                return@dataTask
            }
            if (error != null) {
                DispatchQueue.main.async { completionHandler(. failure (.networkError(error = error!!))) }
                return@dataTask
            }
            do {
                val

                object = this.decoder.decode(T.self, from = data)
                DispatchQueue.main.async { completionHandler(. success (object)) }
            } catch val error {
                DispatchQueue.main.async {
                    #if DEBUG
                    print("JSON Decoding Error: ${error}")
                    #endif
                    completionHandler(. failure (.jsonDecodingError(error = error)))
                }
            }
        }
        task.resume()

         */
    }
    fun HttpRequestBuilder.apiUrl(path: String) {
        header(HttpHeaders.CacheControl, io.ktor.client.utils.CacheControl.MAX_AGE)
        url {
            takeFrom(baseURL)
            encodedPath = path
        }
    }
}
