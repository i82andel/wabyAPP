package com.racoon.waby.ui.spot.spothome

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.racoon.waby.R
import com.racoon.waby.data.model.Spot
import kotlinx.android.synthetic.main.spot_item.view.*

class MySpotAdapter(private val context: Context): RecyclerView.Adapter<MySpotAdapter.MyViewHolder>() {

    private var spotMutableList = mutableListOf<Spot>()

    fun setSpotList(data: MutableList<Spot>){
        spotMutableList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.spot_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = spotMutableList[position]
        holder.bindView(currentItem)
    }

    override fun getItemCount(): Int {
        return spotMutableList.size
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindView(spot: Spot){
            Glide.with(context).load(spot.images?.get(0)).into(itemView.circleImageView)
            itemView.tvSpotName.text = spot.name
            itemView.tvSpotDescription.text = spot.description
            itemView.tvSpotWebSite.text = spot.website
        }

    }

}