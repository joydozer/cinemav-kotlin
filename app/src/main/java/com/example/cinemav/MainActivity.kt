package com.example.cinemav

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumptech.glide.Glide
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageClickListener
import com.synnapps.carouselview.ImageListener
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


const val BaseURLDiscover = "https://api.themoviedb.org/3/discover/"
const val TAGDiscover:String = "CHECK_RESPONSE"

interface ApiServiceDiscover {
    @GET("movie")
    fun getDiscoverMovie(): Call<ResponseDiscoverMovie>
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1000)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        val carouselView = findViewById<CarouselView>(R.id.carouselView)
        val carouselViewTopMovie = findViewById<CarouselView>(R.id.carouselView2)
        val carouselViewPopMovie = findViewById<CarouselView>(R.id.carouselView3)

        var client: OkHttpClient? = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3MjRhOWE0OTg4M2Q1OWQwYmU3ZDMzZDU2NjMyMjA5NCIsInN1YiI6IjY1NWNhZTZjN2YwNTQwMThkNmY1ZDU3MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.004GXmbO1lAItGyxHlM69sXYlF473lVwyLBOnvKJca8")
                .build()
            chain.proceed(newRequest)
        }).build()

        val api =  Retrofit.Builder()
            .client(client)
            .baseUrl(BaseURLDiscover)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceDiscover::class.java)
        api.getDiscoverMovie().enqueue(object: Callback<ResponseDiscoverMovie>{
            override fun onResponse(
                call: Call<ResponseDiscoverMovie>,
                response: Response<ResponseDiscoverMovie>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        var posterImages = arrayOf("")
                        var idMovieDisc = arrayOf("")
                        for(movies in it.results!!) {
                            val url = "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + movies?.posterPath
                            posterImages += url
                            idMovieDisc += movies?.id.toString()
                        }
                        val imageListener: ImageListener = object : ImageListener {
                            override fun setImageForPosition(position: Int, imageView: ImageView) {
                                Glide.with(carouselView)
                                        .load(posterImages[position+1])
                                        .into(imageView)
                            }
                        }

                        carouselView.setImageListener(imageListener)
                        carouselView.pageCount = 7
                        carouselView.setImageClickListener(ImageClickListener { position ->
                            val intent = Intent(this@MainActivity, MoviePage::class.java)
                            intent.putExtra("id", idMovieDisc[position+1])
                            intent.putExtra("url_poster", posterImages[position+1])
                            startActivity(intent)
                        })

                    }
                }
            }

            override fun onFailure(call: Call<ResponseDiscoverMovie>, t: Throwable) {
                Log.i(TAGDiscover, "onFailure: ${t.message}")
            }

        })

        val apiTopRated =  Retrofit.Builder()
            .client(client)
            .baseUrl(BaseURLTop)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceTop::class.java)
        apiTopRated.getTopMovie().enqueue(object: Callback<ResponseTopRated> {
            override fun onResponse(
                call: Call<ResponseTopRated>,
                response: Response<ResponseTopRated>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        var posterImages = arrayOf("")
                        var idMovieDisc = arrayOf("")
                        for(movies in it.results!!) {
                            val url = "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + movies?.posterPath
                            posterImages += url
                            idMovieDisc += movies?.id.toString()
                        }
                        val imageListener2: ImageListener = object : ImageListener {
                            override fun setImageForPosition(position: Int, imageView: ImageView) {
                                Glide.with(carouselView)
                                    .load(posterImages[position+1])
                                    .into(imageView)
                            }
                        }

                        carouselViewTopMovie.setImageListener(imageListener2)
                        carouselViewTopMovie.setPageCount(5)
                        carouselViewTopMovie.setImageClickListener(ImageClickListener { position ->
                            val intent = Intent(this@MainActivity, MoviePage::class.java)
                            intent.putExtra("id", idMovieDisc[position+1])
                            intent.putExtra("url_poster", posterImages[position+1])
                            startActivity(intent)
                        })

                    }
                }
            }

            override fun onFailure(call: Call<ResponseTopRated>, t: Throwable) {
                Log.i(TAGPopular, "onFailure: ${t.message}")
            }

        })

        val apiPopMovie =  Retrofit.Builder()
            .client(client)
            .baseUrl(BaseURLPopular)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServicePopular::class.java)
        apiPopMovie.getPopularMovie().enqueue(object: Callback<ResponsePopularMovie> {
            override fun onResponse(
                call: Call<ResponsePopularMovie>,
                response: Response<ResponsePopularMovie>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        var posterImages = arrayOf("")
                        var idMovieDisc = arrayOf("")
                        for(movies in it.results!!) {
                            val url = "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + movies?.posterPath
                            posterImages += url
                            idMovieDisc += movies?.id.toString()
                        }
                        val imageListener3: ImageListener = object : ImageListener {
                            override fun setImageForPosition(position: Int, imageView: ImageView) {
                                Glide.with(carouselView)
                                    .load(posterImages[position+1])
                                    .into(imageView)
                            }
                        }

                        carouselViewPopMovie.setImageListener(imageListener3)
                        carouselViewPopMovie.setPageCount(5)
                        carouselViewPopMovie.setImageClickListener(ImageClickListener { position ->
                            val intent = Intent(this@MainActivity, MoviePage::class.java)
                            intent.putExtra("id", idMovieDisc[position+1])
                            intent.putExtra("url_poster", posterImages[position+1])
                            startActivity(intent)
                        })

                    }
                }
            }

            override fun onFailure(call: Call<ResponsePopularMovie>, t: Throwable) {
                Log.i(TAGPopular, "onFailure: ${t.message}")
            }

        })

        val btnSearchActivity = findViewById<EditText>(R.id.editTextText)
        btnSearchActivity.setOnClickListener {
            val searchActivity = Intent(this, SearchActivity::class.java);
            startActivity(searchActivity);
        }

        val btnTopRated = findViewById<TextView>(R.id.textView9)
        btnTopRated.setOnClickListener {
            val intent = Intent(this, TopRatedActivity::class.java)
            startActivity(intent)
        }

        val btnPopular = findViewById<TextView>(R.id.textView10)
        btnPopular.setOnClickListener {
            val intent = Intent(this, PopularMoviesActivity::class.java)
            startActivity(intent)
        }

    }
}