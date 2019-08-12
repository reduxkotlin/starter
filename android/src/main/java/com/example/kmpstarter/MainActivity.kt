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
import com.example.common.store
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_message.text = helloWordText
        val list = MoviesMenu.nowPlaying
        store.dispatch(MoviesActions().fetchGenres())
        GlobalScope.launch {
            APIService.shared.GET<PaginatedResponse<Movie>>(
                endpoint = APIService.Endpoint.discover,
                params = mapOf("page" to "1", "region" to "us")
            ) {
                onSuccess {
                    MoviesActions.SetMovieMenuList(
                        page = it.page!!,
                        list = list,
                        response = it
                    )
                }
                onFailure {
                    Log.d("Test", it.message)
                }
            }
        }
    }
}
