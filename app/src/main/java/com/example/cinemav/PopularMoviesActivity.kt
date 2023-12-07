package com.example.cinemav

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract.Instances
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BaseURLPopular = "https://api.themoviedb.org/3/movie/"
const val TAGPopular:String = "CHECK_RESPONSES"

interface ApiServicePopular {
    @GET("popular")
    fun getPopularMovie(): Call<ResponsePopularMovie>
}

class PopularMoviesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_movies)

        var client: OkHttpClient? = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3MjRhOWE0OTg4M2Q1OWQwYmU3ZDMzZDU2NjMyMjA5NCIsInN1YiI6IjY1NWNhZTZjN2YwNTQwMThkNmY1ZDU3MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.004GXmbO1lAItGyxHlM69sXYlF473lVwyLBOnvKJca8")
                .build()
            chain.proceed(newRequest)
        }).build()

        val PopMovieRecycle = findViewById<RecyclerView>(R.id.pop_movie_recycler)
        val api =  Retrofit.Builder()
            .client(client)
            .baseUrl(BaseURLPopular)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServicePopular::class.java)
        api.getPopularMovie().enqueue(object: Callback<ResponsePopularMovie> {
            override fun onResponse(
                call: Call<ResponsePopularMovie>,
                response: Response<ResponsePopularMovie>
            ) {
                if(response.isSuccessful){
                    val responsePop = response.body()
                    val dataPop = responsePop?.results
                    val adapterPopMovie = PopMovieAdapter(this@PopularMoviesActivity, dataPop as List<ResultsItemPopular>)
                    PopMovieRecycle.apply {
                        layoutManager = LinearLayoutManager(this@PopularMoviesActivity)
                        setHasFixedSize(true)
                        adapterPopMovie.notifyDataSetChanged()
                        adapter = adapterPopMovie
                    }
                }
            }

            override fun onFailure(call: Call<ResponsePopularMovie>, t: Throwable) {
                Log.i(TAGPopular, "onFailure: ${t.message}")
            }

        })

        val btnBack = findViewById<ImageView>(R.id.imageView)
        btnBack.setOnClickListener{
            finish()
        }
    }
}