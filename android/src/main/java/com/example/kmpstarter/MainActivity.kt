package com.example.kmpstarter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.common.actions.MoviesActions
import com.example.common.helloWordText
import com.example.common.models.Movie
import com.example.common.models.PaginatedResponse
import com.example.common.services.APIService
import com.example.common.state.MoviesMenu
import com.example.common.util.PlatformDispatcher.dispatch
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_message.text = helloWordText
        val list = MoviesMenu.genres
        GlobalScope.launch {
            APIService.shared.GET<PaginatedResponse<Movie>>(
                endpoint = APIService.Endpoint.discover,
                params = mapOf("page" to "0", "region" to "en")
            ) { result ->
                if (result.isSuccess) {
                    MoviesActions.SetMovieMenuList(
                        page = result.getOrNull()?.page!!,
                        list = list,
                        response = result.getOrThrow()
                    )
                } else {
                    Log.d("Test", result.exceptionOrNull()?.message)
                }
            }
        }
    }
}
