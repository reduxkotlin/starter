package com.example.common

import org.reduxkotlin.Reducer
import org.reduxkotlin.createStore

val helloWordText = "Hello Kotlin MP"

data class AppState(val count: Int = 0)

class ButtonTap

val reducer: Reducer<AppState> = {state, action ->
    when (action) {
        is ButtonTap -> state.copy(count = state.count + 1)
        else -> state
    }
}

val store = createStore(reducer, AppState())

data class MainViewState(val counterText: String)

fun AppState.toMainViewState() = MainViewState(count.toString())


