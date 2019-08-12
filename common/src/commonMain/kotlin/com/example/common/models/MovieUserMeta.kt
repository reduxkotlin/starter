package com.example.common.models

import com.soywiz.klock.DateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


//
//  MovieUserMeta.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 28/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
@Serializable
data class MovieUserMeta(
    @Transient
    var addedToList: DateTime? = null)
