package com.example.cinemav

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DiscoverCarouselAdapter (val dataDiscoverMovie: List<ResultsItem>): RecyclerView.Adapter<DiscoverCarouselAdapter.MyViewRecycler>() {
    class MyViewRecycler (view: View): RecyclerView.ViewHolder(view) {
        val posterMovie = view.findViewById<ImageView>(R.id.poster_movie_carousel)
        val titleMovie = view.findViewById<TextView>(R.id.title_movie_carousel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewRecycler {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_movie, parent, false)
        return MyViewRecycler(view)
    }

    override fun getItemCount(): Int {
        if(dataDiscoverMovie != null) {
            return dataDiscoverMovie.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: MyViewRecycler, position: Int) {
        holder.titleMovie.text = dataDiscoverMovie?.get(position)?.title
        /*for(genre in dataPopMovie?.get(position)?.genreIds!!) {
            holder.genreMovie.append(dataPopMovie?.get(position)?.genreIds!![genre!!].toString())
        }*/

        val url = "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + dataDiscoverMovie?.get(position)?.posterPath

        Glide.with(holder.posterMovie)
            .load(url)
            .error(R.drawable.ic_launcher_background)
            .into(holder.posterMovie)

        holder.itemView.setOnClickListener{
            val title = dataDiscoverMovie?.get(position)?.title
            Toast.makeText(holder.itemView.context, "${title}", Toast.LENGTH_SHORT).show()
        }
    }
}