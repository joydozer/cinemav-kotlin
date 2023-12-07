package com.example.cinemav

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TopMovieAdapter(val context: Context, val dataTopMovie: List<ResultsItemTopRated>): RecyclerView.Adapter<TopMovieAdapter.MyViewRecycler>() {
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
        if(dataTopMovie != null) {
            return dataTopMovie.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: MyViewRecycler, position: Int) {
        holder.titleMovie.text = dataTopMovie?.get(position)?.title
        holder.genreMovie.text = dataTopMovie?.get(position)?.genreIds?.get(0).toString()
        /*for(genre in dataPopMovie?.get(position)?.genreIds!!) {
            holder.genreMovie.append(dataPopMovie?.get(position)?.genreIds!![genre!!].toString())
        }*/

        val url = "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + dataTopMovie?.get(position)?.posterPath

        Glide.with(holder.posterMovie)
            .load(url)
            .error(R.drawable.ic_launcher_background)
            .into(holder.posterMovie)

        holder.itemView.setOnClickListener{
            val intent = Intent(context, MoviePage::class.java)
            intent.putExtra("id", dataTopMovie?.get(position)?.id.toString())
            intent.putExtra("url_poster", url)
            context.startActivity(intent)
        }
    }
}