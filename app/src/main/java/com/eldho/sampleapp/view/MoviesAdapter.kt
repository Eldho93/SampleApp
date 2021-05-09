package com.eldho.sampleapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView


import com.bumptech.glide.Glide
import com.eldho.sampleapp.R
import com.eldho.sampleapp.model.moviesmodel.Search
import kotlinx.android.synthetic.main.item_recycler.view.*
import java.util.*
import kotlin.collections.ArrayList


class MoviesAdapter(
    private val items: List<Search>,

    ) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>(), Filterable {

    var itemsFilterList = ArrayList<Search>()

    init {
        itemsFilterList = items as ArrayList<Search>
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler, parent, false)
        )
    }

    override fun getItemCount(): Int = itemsFilterList.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(itemsFilterList[position])
    }

    inner class MoviesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: Search) {
            with(view) {
                text_label.text = item.Title

                Glide.with(view)
                    .load(item.Poster)
                    .centerCrop()
                    .placeholder(R.drawable.profile)
                    .fallback(R.drawable.profile)
                    .into(img_item)

            }

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    itemsFilterList = items as ArrayList<Search>
                } else {
                    val resultList = ArrayList<Search>()
                    for (row in items) {
                        if (row.Title!!.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    itemsFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = itemsFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemsFilterList = results?.values as ArrayList<Search>
                notifyDataSetChanged()
            }

        }
    }
}