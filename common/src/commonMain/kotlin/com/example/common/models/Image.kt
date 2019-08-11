package com.example.common.models

import com.benasher44.uuid.Uuid


//
//  MovieImage.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 21/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
data class ImageData(
    val id: Uuid = Uuid(),
    val aspect_ratio: Float,
    val file_path: String,
    val height: Int,
    val width: Int
)
