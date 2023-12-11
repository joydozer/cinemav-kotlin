package com.example.cinemav

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SearchMovieAdapter(val context: Context, val dataSearchMovie: List<ResultsItemSearch>): RecyclerView.Adapter<SearchMovieAdapter.MyViewRecycler>() {
    class MyViewRecycler (view: View): RecyclerView.ViewHolder(view) {
        val posterMovie = view.findViewById<ImageView>(R.id.poster_movie)
        val titleMovie = view.findViewById<TextView>(R.id.title_movie)
        val genreMovie = view.findViewById<TextView>(R.id.genre_movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewRecycler {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_movie, parent, false)
        return MyViewRecycler(view)
    }

    override fun getItemCount(): Int {
        if(dataSearchMovie != null) {
            return dataSearchMovie.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: MyViewRecycler, position: Int) {
        holder.titleMovie.text = dataSearchMovie?.get(position)?.title
        holder.genreMovie.text = ""
        for (i in 0 until dataSearchMovie?.get(position)?.genreIds!!.size) {
            when (dataSearchMovie?.get(position)?.genreIds?.get(i)) {
                28 -> holder.genreMovie.append("Action ")
                12 -> holder.genreMovie.append("Adventure ")
                16 -> holder.genreMovie.append("Animation ")
                35 -> holder.genreMovie.append("Comedy ")
                80 -> holder.genreMovie.append("Crime ")
                99 -> holder.genreMovie.append("Documentary ")
                18 -> holder.genreMovie.append("Drama ")
                10751 -> holder.genreMovie.append("Family ")
                14 -> holder.genreMovie.append("Fantasy ")
                36 -> holder.genreMovie.append("History ")
                27 -> holder.genreMovie.append("Horror ")
                10402 -> holder.genreMovie.append("Music ")
                9648 -> holder.genreMovie.append("Mystery ")
                10749 -> holder.genreMovie.append("Romance ")
                878 -> holder.genreMovie.append("Science Fiction ")
                10770 -> holder.genreMovie.append("TV Movie ")
                53 -> holder.genreMovie.append("Thriller ")
                10752 -> holder.genreMovie.append("War ")
                37 -> holder.genreMovie.append("Western ")
                else -> {
                    holder.genreMovie.append("Error")
                }
            }
        }
        val url = "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + dataSearchMovie?.get(position)?.posterPath

        Glide.with(holder.posterMovie)
            .load(url)
            .error(R.drawable.ic_launcher_background)
            .into(holder.posterMovie)

        holder.itemView.setOnClickListener{
            val intent = Intent(context, MoviePage::class.java)
            intent.putExtra("id", dataSearchMovie?.get(position)?.id.toString())
            intent.putExtra("url_poster", url)
            context.startActivity(intent)
        }
    }
}