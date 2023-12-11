package com.example.cinemav

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
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
import retrofit2.http.Path
import retrofit2.http.Query

const val BaseURLMovieSearch = "https://api.themoviedb.org/3/search/"
const val TAGSearch:String = "CHECK_RESPONSE"

interface ApiServiceMovieSearch {
    //@GET("movie?query={search_query}&include_adult=false&language=en-US&page=1")
    //language, page, include_adult
    @GET("movie")
    fun getMovieSearch(@Query("query") id:String?, @Query("page") id2:Int?, @Query("language") id3:String?, @Query("include_adult") id4:String?): Call<ResponseMovieSearch>
}

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_page)
        val imgCenter = findViewById<ImageView>(R.id.imageView4)
        val inputEditText = findViewById<EditText>(R.id.editTextText2)
        val searchMovieRecycle = findViewById<RecyclerView>(R.id.searchMovieRecycle)
        val includeAdult = "false" //always false for child protection
        val language = "en-US"
        val pageSearch = 1
        //val handler = Handler()

        var client: OkHttpClient? = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3MjRhOWE0OTg4M2Q1OWQwYmU3ZDMzZDU2NjMyMjA5NCIsInN1YiI6IjY1NWNhZTZjN2YwNTQwMThkNmY1ZDU3MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.004GXmbO1lAItGyxHlM69sXYlF473lVwyLBOnvKJca8")
                .build()
            chain.proceed(newRequest)
        }).build()


        val api =  Retrofit.Builder()
            .client(client)
            .baseUrl(BaseURLMovieSearch)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceMovieSearch::class.java)


        inputEditText.addTextChangedListener(object :  TextWatcher {
            var timer: CountDownTimer? = null
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(s?.length!! > 0) {
                    imgCenter.setImageResource(android.R.color.transparent)
                } else {
                    imgCenter.setImageResource(R.drawable.search_bg)
                }
                timer?.cancel()
                timer = object : CountDownTimer(2000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                    }

                    override fun onFinish() {
                        api.getMovieSearch(s.toString(), pageSearch, language, includeAdult).enqueue(object: Callback<ResponseMovieSearch> {
                            override fun onResponse(
                                call: Call<ResponseMovieSearch>,
                                response: Response<ResponseMovieSearch>
                            ) {
                                if (response.isSuccessful) {
                                    val responseSearch = response.body()
                                    val dataSearch = responseSearch?.results
                                    val adapterMovieSearch = SearchMovieAdapter(this@SearchActivity, dataSearch as List<ResultsItemSearch>)
                                    searchMovieRecycle.apply {
                                        layoutManager = LinearLayoutManager(this@SearchActivity)
                                        setHasFixedSize(true)
                                        adapterMovieSearch.notifyDataSetChanged()
                                        adapter = adapterMovieSearch
                                    }
                                    /*response.body()?.let {
                                        for(search in it.results!!) {
                                            Log.i(TAGSearch, "onFailure: ${search?.title}")
                                        }
                                    }*/
                                }
                            }

                            override fun onFailure(call: Call<ResponseMovieSearch>, t: Throwable) {
                                Log.i(TAGSearch, "onFailure: ${t.message}")
                            }
                        })
                    }

                }.start()
            }

        })

        val btnBack = findViewById<ImageView>(R.id.imageView2)
        btnBack.setOnClickListener{
            finish()
        }
    }
}