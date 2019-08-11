package com.example.common.middlewares

import com.example.common.state.AppState
import org.reduxkotlin.Middleware
import org.reduxkotlin.middleware

val loggingMiddleware: Middleware<AppState> = { store ->
    { next ->
        { action ->

            next(action)
        }
    }

}