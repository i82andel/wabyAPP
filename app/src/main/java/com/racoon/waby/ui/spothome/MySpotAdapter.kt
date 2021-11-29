package com.racoon.waby.ui.spothome

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.racoon.waby.R
import com.racoon.waby.data.model.Spot

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
        val currenItem = spotMutableList[position]

        holder.spotName.text = currenItem.name
        holder.spotDescription.text = currenItem.description
        holder.spotWebsite.text = currenItem.website
    }

    override fun getItemCount(): Int {
        return spotMutableList.size
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindView(spot: Spot){



        }
        val spotName : TextView = itemView.findViewById(R.id.tvSpotName)
        val spotDescription : TextView = itemView.findViewById(R.id.tvSpotDescription)
        val spotWebsite : TextView = itemView.findViewById(R.id.tvSpotWebSite)

    }

}