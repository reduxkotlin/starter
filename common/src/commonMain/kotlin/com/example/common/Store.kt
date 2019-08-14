package com.example.common

import com.example.common.middlewares.loggingMiddleware
import com.example.common.reducers.appStateReducer
import com.example.common.state.AppState
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.createStore
import org.reduxkotlin.createThunkMiddleware

fun createStore() = createStore(::appStateReducer, AppState(), applyMiddleware(createThunkMiddleware(),
    loggingMiddleware))