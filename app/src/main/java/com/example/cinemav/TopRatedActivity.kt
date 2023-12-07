package com.example.cinemav

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
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

const val BaseURLTop = "https://api.themoviedb.org/3/movie/"
const val TAGTop:String = "CHECK_RESPONSE"

interface ApiServiceTop {
    @GET("top_rated")
    fun getTopMovie(): Call<ResponseTopRated>
}

class TopRatedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_rated)

        //getTopRated()

        var client: OkHttpClient? = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3MjRhOWE0OTg4M2Q1OWQwYmU3ZDMzZDU2NjMyMjA5NCIsInN1YiI6IjY1NWNhZTZjN2YwNTQwMThkNmY1ZDU3MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.004GXmbO1lAItGyxHlM69sXYlF473lVwyLBOnvKJca8")
                .build()
            chain.proceed(newRequest)
        }).build()

        val TopMovieRecycle = findViewById<RecyclerView>(R.id.top_movie_recycler)
        val api =  Retrofit.Builder()
            .client(client)
            .baseUrl(BaseURLTop)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceTop::class.java)
        api.getTopMovie().enqueue(object: Callback<ResponseTopRated> {
            override fun onResponse(
                call: Call<ResponseTopRated>,
                response: Response<ResponseTopRated>
            ) {
                if(response.isSuccessful){
                    val responseTop = response.body()
                    val dataTop = responseTop?.results
                    val adapterTopMovie = TopMovieAdapter(this@TopRatedActivity, dataTop as List<ResultsItemTopRated>)
                    TopMovieRecycle.apply {
                        layoutManager = LinearLayoutManager(this@TopRatedActivity)
                        setHasFixedSize(true)
                        adapterTopMovie.notifyDataSetChanged()
                        adapter = adapterTopMovie
                    }
                }
            }

            override fun onFailure(call: Call<ResponseTopRated>, t: Throwable) {
                Log.i(TAGPopular, "onFailure: ${t.message}")
            }

        })

        val btnBack = findViewById<ImageView>(R.id.imageView3)
        btnBack.setOnClickListener{
            finish()
        }
    }
    /*
    var client: OkHttpClient? = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3MjRhOWE0OTg4M2Q1OWQwYmU3ZDMzZDU2NjMyMjA5NCIsInN1YiI6IjY1NWNhZTZjN2YwNTQwMThkNmY1ZDU3MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.004GXmbO1lAItGyxHlM69sXYlF473lVwyLBOnvKJca8")
            .build()
        chain.proceed(newRequest)
    }).build()

    fun getTopRated() {
        val api =  Retrofit.Builder()
            .client(client)
            .baseUrl(BaseURLTop)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceTop::class.java)
        api.getTopMovie().enqueue(object: Callback<ResponseTopRated> {
            override fun onResponse(
                call: Call<ResponseTopRated>,
                response: Response<ResponseTopRated>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        for(movies in it.results!!) {
                            Log.i(TAGPopular, "onResponse ${movies?.title}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseTopRated>, t: Throwable) {
                Log.i(TAGPopular, "onFailure: ${t.message}")
            }

        })
    }*/
}