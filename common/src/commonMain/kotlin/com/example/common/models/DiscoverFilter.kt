package com.example.common.models


//
//  DiscoverFilter.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 23/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
data class DiscoverFilter(
    val year: Int,
    val startYear: Int?,
    val endYear: Int?,
    val sort: String,
    val genre: Int?,
    val region: String?
) {
    companion object {

        fun randomFilter(): DiscoverFilter =
            DiscoverFilter(
                year = randomYear(),
                startYear = null,
                endYear = null,
                sort = randomSort(),
                genre = null,
                region = null
            )

        fun randomYear(): Int {
            val calendar = Calendar.current
            random()
            return random(in = 1950 until calendar.component(. year, from = Date()))
        }

        fun randomSort(): String {
            val sortBy =
                listOf("popularity.desc", "popularity.asc", "vote_average.asc", "vote_average.desc")
            return sortBy[Int.random(in = 0 until sortBy.size)]
        }

        fun randomPage(): Int =
            Int.random(in = 1 until 20)
    }


    fun toParams(): Map<String, String> {
        var params = mutableMapOf<String, String>()
        val startYear = startYear
        val endYear = endYear
        if (startYear != null && endYear != null) {
            params["primary_release_date.gte"] = "${startYear}"
            params["primary_release_date.lte"] = "${endYear}"
        } else {
            params["year"] = "${year}"
        }
        val genre = genre
        if (genre != null) {
            params["with_genres"] = "${genre}"
        }
        val region = region
        if (region != null) {
            params["region"] = region
        }
        params["page"] = "${DiscoverFilter.randomPage()}"
        params["sort_by"] = sort
        params["language"] = "en-US"
        return params
    }

    fun toText(genres: List<Genre>): String {
        var text = String()
        val startYear = startYear
        val endYear = endYear
        if (startYear != null && endYear != null) {
            text += "$startYear-$endYear"
        } else {
            text = "$text · Random"
        }
        val genre = genre
        val stateGenre = genres.firstOrNull { realGenre ->
            realGenre.id == genre
        }
        if (genre != null && stateGenre != null) text += " · ${stateGenre.name}"
        val region = region
        if (region != null) {
            text += " · $region"
        }
        return text
    }
}
