package com.example.common

import com.example.common.state.AppState
import org.reduxkotlin.Dispatcher
import org.reduxkotlin.GetState
import org.reduxkotlin.Thunk
import org.reduxkotlin.createThunk

fun thunk(thunkLambda: (dispatch: Dispatcher, getState: GetState<AppState>, extraArgument: Any?) -> Any): Thunk<AppState> =
    createThunk(thunkLambda)
