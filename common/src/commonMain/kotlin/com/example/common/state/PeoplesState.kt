package com.example.common.state

import com.example.common.models.People

//
//  CastsState.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 09/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
data class PeoplesState(
    var peoples: Map<Int, People> = mapOf(),
    var peoplesMovies: Map<Int, List<Int>> = mapOf(),
    var search: Map<String, List<Int>> = mapOf(),
    var popular: List<Int> = listOf(),
/// [PeopleId: [MovieId:  Character]]
    var casts: Map<Int, Map<Int, String>> = mapOf(),
/// [PeopleId: [MovieId:  Character]]
    var crews: Map<Int, Map<Int, String>> = mapOf(),
    var fanClub: Set<Int> = setOf()
) {
    enum class CodingKeys(val rawValue: String) {
        peoples("peoples"),
        fanClub("fanClub");

        companion object {
            operator fun invoke(rawValue: String) =
                CodingKeys.values().firstOrNull { it.rawValue == rawValue }
        }
    }
}