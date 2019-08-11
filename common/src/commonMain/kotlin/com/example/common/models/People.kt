package com.example.common.models

import kotlinx.serialization.Serializable


//
//  Cast.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 09/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
@Serializable
data class People(
    val id: Int,
    val name: String,
    var character: String? = null,
    var department: String? = null,
    val profile_path: String?,
    val known_for_department: String?,
    var known_for: List<KnownFor>? = null,
    val also_known_as: List<String>?,
    val birthDay: String?,
    val deathDay: String?,
    val place_of_birth: String?,
    val biography: String?,
    val popularity: Double?,
    var images: List<ImageData>? = null
) {

    @Serializable
    data class KnownFor(
        val id: Int,
        val original_title: String?,
        val poster_path: String?
    )
}

val People.knownForText: String?
    get() {
        val knownFor = known_for ?: return null
        val names = knownFor.filter { it.original_title != null }.map { it.original_title!! }
        return names.joinToString(separator = ", ")
    }
