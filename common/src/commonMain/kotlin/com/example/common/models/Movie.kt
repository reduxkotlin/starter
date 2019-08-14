package com.example.common.models

import com.benasher44.uuid.Uuid
import com.soywiz.klock.DateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


//
//  Movie.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 06/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
@Serializable
data class Movie(
    val id: String,
    val original_title: String,
    val title: String,
    val overview: String,
    val poster_path: String? = null,
    val backdrop_path: String? = null,
    val popularity: Float,
    val vote_average: Float,
    val vote_count: Int,
    val release_date: String? = null,
//val dateFormatter: DateFormatter = {
//    val formatter = DateFormatter()
//    formatter.dateFormat = "yyy-MM-dd"
//    formatter
//}(),
    val genres: List<Genre>? = null,
    val runtime: Int? = null,
    val status: String? = null,
    var keywords: Keywords? = null,
    var images: MovieImages? = null,
    var production_countries: List<ProductionCountry>? = null,
    var character: String? = null,
    var department: String? = null
) {
    val userTitle: String
        get() = "" //TODO if (AppUserDefaults.alwaysOriginalTitle) original_title else title
    val releaseDate: DateTime?
        get() = null //TODO if (release_date != null) Movie.dateFormatter.date(from = release_date!!) else DateTime.now()

    @Serializable
    data class Keywords(val keywords: List<Keyword>? = null)

    @Serializable
    data class MovieImages(
        val posters: List<ImageData>? = null,
        val backdrops: List<ImageData>? = null
    )

    @Serializable
    data class ProductionCountry(
        @Transient
        val id: Uuid = Uuid(),
        val name: String
    )
}

val sampleMovie = Movie(
    id = "0",
    original_title = "Test movie Test movie Test movie Test movie Test movie Test movie Test movie ",
    title = "Test movie Test movie Test movie Test movie Test movie Test movie Test movie  Test movie Test movie Test movie",
    overview = "Test desc",
    poster_path = "/uC6TTUhPpQCmgldGyYveKRAu8JN.jpg",
    backdrop_path = "/nl79FQ8xWZkhL3rDr1v2RFFR6J0.jpg",
    popularity = 50.5f,
    vote_average = 8.9f,
    vote_count = 1000,
    release_date = "1972-03-14",
    genres = listOf( Genre(id = "0", name = "test")),
    runtime = 80,
    status = "released"
)