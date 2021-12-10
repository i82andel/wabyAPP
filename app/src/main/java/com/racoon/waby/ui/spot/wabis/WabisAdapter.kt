package com.racoon.waby.ui.spot.wabis

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.racoon.waby.R
import com.racoon.waby.data.model.Spot
import com.racoon.waby.data.model.User
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.spot_item.view.*
import kotlinx.android.synthetic.main.wabi_item.view.*

class WabisAdapter (private val context: Context): RecyclerView.Adapter<WabisAdapter.MyWabiViewHolder>()  {

    private var wabiMutableList = mutableListOf<User>()

    fun setWabiList(data: MutableList<User>){
        wabiMutableList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWabiViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.wabi_item, parent, false)
        return MyWabiViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyWabiViewHolder, position: Int) {
        val currentItem = wabiMutableList[position]
        holder.bindView(currentItem)
    }

    override fun getItemCount(): Int {
        return wabiMutableList.size
    }

    inner class MyWabiViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindView(user: User){
            Glide.with(context).load(user.images?.get(0)).into(itemView.MatchImage)
            itemView.MatchName.text = user.userName
            itemView.Matchid.text = user.getAge()
        }

    }


}