package com.example.common.models

import kotlinx.serialization.Serializable


//
//  PaginatedResponse.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 06/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
@Serializable
data class PaginatedResponse<T>(
    val page: Int?,
    val total_results: Int?,
    val total_pages: Int?,
    val results: List<T>
)
