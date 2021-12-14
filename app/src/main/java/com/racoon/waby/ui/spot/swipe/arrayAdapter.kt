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
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
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
        val tag1 = convertView!!.findViewById<View>(R.id.tag1) as TextView
        val tag2 = convertView!!.findViewById<View>(R.id.tag2) as TextView
        val tag3 = convertView!!.findViewById<View>(R.id.tag3) as TextView
        val tag4 = convertView!!.findViewById<View>(R.id.tag4) as TextView
        val tag5 = convertView!!.findViewById<View>(R.id.tag5) as TextView
        val Tag1 = convertView!!.findViewById<View>(R.id.Tag1) as CardView
        val Tag2 = convertView!!.findViewById<View>(R.id.Tag2) as CardView
        val Tag3 = convertView!!.findViewById<View>(R.id.Tag3) as CardView
        val Tag4 = convertView!!.findViewById<View>(R.id.Tag4) as CardView
        val Tag5 = convertView!!.findViewById<View>(R.id.Tag5) as CardView


        name.text = card_item!!.name
        descripcion.text = card_item!!.description

        /*val myLinearLayoutManager = GridLayoutManager(context, 2, 0, false)

        tags_items.layoutManager = myLinearLayoutManager
        val adapter = card_item!!.tags?.let { com.racoon.waby.ui.spot.swipe.TagAdapter(it) }
        tags_items.adapter = adapter*/

        var countNumber = 0
        var tags = card_item.tags

        if ( tags != null) {
            for (tag in tags){

                when(countNumber){

                    0 -> {tag1.text = tag
                        Tag1.visibility = View.VISIBLE
                    }
                    1 -> {tag2.text = tag
                        Tag2.visibility = View.VISIBLE
                    }
                    2 ->{tag3.text = tag
                        Tag3.visibility = View.VISIBLE
                    }
                    3 -> {tag4.text = tag
                        Tag4.visibility = View.VISIBLE
                    }
                    4 -> {tag5.text = tag
                        Tag5.visibility = View.VISIBLE
                    }
                }
                countNumber++
            }
        }



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