package com.example.common.models

import kotlinx.serialization.Serializable


//
//  Genre.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 15/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
@Serializable
data class Genre(
    val id: Int,
    val name: String
)
