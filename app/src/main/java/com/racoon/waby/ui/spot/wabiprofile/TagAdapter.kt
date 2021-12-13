package com.racoon.waby.ui.spot.wabiprofile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.racoon.waby.R
import com.racoon.waby.data.model.Tag
import kotlinx.android.synthetic.main.tag_item.view.*

class TagAdapter(val tags : List<String>):RecyclerView.Adapter<TagAdapter.TagHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TagHolder(layoutInflater.inflate(R.layout.tag_item, parent, false))
    }

    override fun onBindViewHolder(holder: TagHolder, position: Int) {
        holder.render(tags[position])
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    class TagHolder(val view: View):RecyclerView.ViewHolder(view){
        fun render(tag: String){
            view.tag_text.text = tag
        }
    }
}