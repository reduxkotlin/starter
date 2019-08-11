package com.example.common.reducers

import com.example.common.state.AppState


//
//  AppReducer.swift
//  MovieSwift
//
//  Created by Thomas Ricouard on 26/06/2019.
//  Copyright © 2019 Thomas Ricouard. All rights reserved.
//
fun appStateReducer(state: AppState, action: Any) : AppState {
    state.moviesState = moviesStateReducer(state = state.moviesState, action = action)
    state.peoplesState = peoplesStateReducer(state = state.peoplesState, action = action)
    return state
}
