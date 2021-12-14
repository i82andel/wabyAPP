package com.racoon.waby.ui.spot.swipe

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.racoon.waby.R
import com.racoon.waby.data.model.User
import com.racoon.waby.ui.home.myprofile.TagAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.wabi_item.view.*

class arrayAdapter(context: Context?, resourceId: Int, items: List<User?>?) : ArrayAdapter<User?>(context!!, resourceId, items!!) {

    private var userList = items

    @SuppressLint("WrongConstant")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val card_item = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false)
        }
        val name = convertView!!.findViewById<View>(R.id.name) as TextView
        val image = convertView.findViewById<View>(R.id.image) as ImageView
        val descripcion = convertView.findViewById<View>(R.id.descriptioncard) as TextView
        val tags_items = convertView.findViewById<View>(R.id.tags_items) as RecyclerView

        name.text = card_item!!.name
        descripcion.text = card_item!!.description

        tags_items.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        val adapter = card_item!!.tags?.let { com.racoon.waby.ui.spot.swipe.TagAdapter(it) }
        tags_items.adapter = adapter

        when (card_item.images) {
            "default" -> Glide.with(convertView.context).load(R.mipmap.ic_launcher).into(image)
            else -> {
                val storageReference = FirebaseStorage.getInstance()
                val gsReference = storageReference.getReferenceFromUrl(card_item.images)
                gsReference.downloadUrl.addOnSuccessListener {
                    Glide.with(context).load(it).into(image)
                }
            }
        }
        return convertView
    }
}