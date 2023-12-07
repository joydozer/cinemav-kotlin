package com.example.cinemav

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET


//https://www.youtube.com/watch?v=sKRLCwGTHu8
//https://www.youtube.com/watch?v=I3sfnblByiY
//https://www.youtube.com/watch?v=sRLunCZX2Uc
//https://www.youtube.com/watch?v=6CgkDGIzUC0

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

        val carouselView = findViewById(R.id.carouselView) as CarouselView

        //getDiscover()

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
                        val imageListener: ImageListener = object : ImageListener {
                            override fun setImageForPosition(position: Int, imageView: ImageView) {
                                Glide.with(this@MainActivity)
                                        .load("https://image.tmdb.org/t/p/w600_and_h900_bestv2/qNBAXBIQlnOThrVvA6mA2B5ggV6.jpg")
                                        .into(imageView)
                            }
                        }

                        //carouselView.setPageCount(it.results!!.size)
                        //carouselView.setImageListener(imageListener)

                        for(movies in it.results!!) {
                            //Log.i(TAGDiscover, "onResponse ${movies?.title}")
                            /*val url = "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + movies?.posterPath
                            )
                            val posterImage = intArrayOf(
                                Glide.with()
                                    .load(url),*/
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseDiscoverMovie>, t: Throwable) {
                Log.i(TAGDiscover, "onFailure: ${t.message}")
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

    /*

    var client: OkHttpClient? = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3MjRhOWE0OTg4M2Q1OWQwYmU3ZDMzZDU2NjMyMjA5NCIsInN1YiI6IjY1NWNhZTZjN2YwNTQwMThkNmY1ZDU3MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.004GXmbO1lAItGyxHlM69sXYlF473lVwyLBOnvKJca8")
            .build()
        chain.proceed(newRequest)
    }).build()

    fun getDiscover() {
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
                        for(movies in it.results!!) {
                            //Log.i(TAGDiscover, "onResponse ${movies?.title}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseDiscoverMovie>, t: Throwable) {
                Log.i(TAGDiscover, "onFailure: ${t.message}")
            }

        })
    }*/
}