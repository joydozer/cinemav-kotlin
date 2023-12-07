package com.example.cinemav

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val BaseURLMovieDetail = "https://api.themoviedb.org/3/"
const val TAGDetail:String = "CHECK_RESPONSE"


interface ApiServiceMovieDetail {
    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") id:String?): Call<ResponseMovieDetail>
}

class MoviePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_page)
        val idMovie = intent.getStringExtra("id")
        val posterPath = intent.getStringExtra("url_poster")
        val movieTitle = findViewById<TextView>(R.id.textView4)
        val moviePoster = findViewById<ImageView>(R.id.poster_movie_page)
        val durationMovie = findViewById<TextView>(R.id.duration_movie)
        val releaseDate = findViewById<TextView>(R.id.release_date_movie)
        val overviewMovie = findViewById<TextView>(R.id.overview_movie_text)

        var client: OkHttpClient? = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3MjRhOWE0OTg4M2Q1OWQwYmU3ZDMzZDU2NjMyMjA5NCIsInN1YiI6IjY1NWNhZTZjN2YwNTQwMThkNmY1ZDU3MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.004GXmbO1lAItGyxHlM69sXYlF473lVwyLBOnvKJca8")
                .build()
            chain.proceed(newRequest)
        }).build()


        val api =  Retrofit.Builder()
            .client(client)
            .baseUrl(BaseURLMovieDetail)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceMovieDetail::class.java)
        api.getMovieDetail(idMovie).enqueue(object: Callback<ResponseMovieDetail> {
            override fun onResponse(
                call: Call<ResponseMovieDetail>,
                response: Response<ResponseMovieDetail>
            ) {
                if(response.isSuccessful){
                    Log.i(TAGDetail, "onResponse ${response.body()?.title}")
                    movieTitle.text = response.body()?.title
                    val h: Int = response.body()?.runtime!! / 60
                    val m: Int = response.body()?.runtime!! % 60
                    val newtime = "$h Hours $m Minutes"
                    durationMovie.text = newtime
                    releaseDate.text = response.body()?.releaseDate
                    overviewMovie.text = response.body()?.overview
                    Glide.with(moviePoster)
                        .load(posterPath)
                        .error(R.drawable.ic_launcher_background)
                        .into(moviePoster)
                    moviePoster.setColorFilter(Color.argb(100, 0, 0,0))
                }
            }

            override fun onFailure(call: Call<ResponseMovieDetail>, t: Throwable) {
                Log.i(TAGDetail, "onFailure: ${t.message}")
            }

        })

        val btnBack = findViewById<ImageView>(R.id.imageView5)
        btnBack.setOnClickListener{
            finish()
        }
    }
}