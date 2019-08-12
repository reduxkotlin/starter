package com.example.common.models

import kotlinx.serialization.Serializable


//
//  CustomList.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 18/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
@Serializable
data class CustomList(
    val id: Int,
    var name: String,
    var cover: Int? = null,
    var movies: MutableSet<Int>
)
