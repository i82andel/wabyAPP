package com.racoon.waby.ui.spot.swipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.racoon.waby.R
import com.racoon.waby.data.model.User
import kotlinx.android.synthetic.main.wabi_item.view.*

class arrayAdapter(context: Context?, resourceId: Int, items: List<User?>?) : ArrayAdapter<User?>(context!!, resourceId, items!!) {

    private var userList = items

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val card_item = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false)
        }
        val name = convertView!!.findViewById<View>(R.id.name) as TextView
        val image = convertView.findViewById<View>(R.id.image) as ImageView

        name.text = card_item!!.name
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