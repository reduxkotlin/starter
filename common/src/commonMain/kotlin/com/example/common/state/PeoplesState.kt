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
    var peoples: MutableMap<Int, People> = mutableMapOf(),
    var peoplesMovies: MutableMap<Int, List<Int>> = mutableMapOf(),
    var search: MutableMap<String, List<Int>> = mutableMapOf(),
    var popular: List<Int> = listOf(),
/// [PeopleId: [MovieId:  Character]]
    var casts: MutableMap<Int, MutableMap<Int, String>> = mutableMapOf(),
/// [PeopleId: [MovieId:  Character]]
    var crews: MutableMap<Int, MutableMap<Int, String>> = mutableMapOf(),
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

    fun withPeopleId(peopleId: Int) = peoples[peopleId]

    fun filterPeoples(predicate: (People) -> Boolean) = peoples.values.filter(predicate)

    val characters
        get() = peoples.values.filter { it.character != null }

    val credits
        get() = peoples.values.filter { it.department != null }

}