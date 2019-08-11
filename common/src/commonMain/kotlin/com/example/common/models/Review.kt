package com.example.common.models

import kotlinx.serialization.Serializable


//
//  Review.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 16/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
@Serializable
data class Review(
    val id: String,
    val author: String,
    val content: String
)
