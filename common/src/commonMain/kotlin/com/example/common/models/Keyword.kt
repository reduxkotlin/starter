package com.example.common.models

import kotlinx.serialization.Serializable


//
//  Keyword.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 16/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
@Serializable
data class Keyword(
    val id: String,
    val name: String
)
