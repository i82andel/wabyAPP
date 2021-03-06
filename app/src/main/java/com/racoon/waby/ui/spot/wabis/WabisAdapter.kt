package com.racoon.waby.ui.spot.wabis

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth

import com.racoon.waby.R
import com.racoon.waby.data.model.Spot
import com.racoon.waby.data.model.User
import com.racoon.waby.data.repository.WabiRepository
import com.racoon.waby.ui.spot.SpotActivity
import com.racoon.waby.ui.spot.spothome.SpotHomeViewModel
import com.racoon.waby.ui.spot.wabiprofile.WabiProfileActivity
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.spot_item.view.*
import kotlinx.android.synthetic.main.wabi_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WabisAdapter(private val context: Context) :
    RecyclerView.Adapter<WabisAdapter.MyWabiViewHolder>() {


    private var wabiMutableList = mutableListOf<User>()
    private var wabiRepository = WabiRepository()
    val uid = Firebase.auth.currentUser?.uid as String

    fun setWabiList(data: MutableList<User>) {
        wabiMutableList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWabiViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.wabi_item, parent, false)
        return MyWabiViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyWabiViewHolder, position: Int) {
        val currentItem = wabiMutableList[position]
        holder.bindView(currentItem)

        holder.itemView.setOnClickListener {
            val intent = Intent(context,WabiProfileActivity::class.java).putExtra("idUser",currentItem.idUser)

            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return wabiMutableList.size
    }

    inner class MyWabiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(user: User) {
            println(user.images)
            val storageReference = FirebaseStorage.getInstance()
            val gsReference = storageReference.getReferenceFromUrl(user.images)
            gsReference.downloadUrl.addOnSuccessListener {
                Glide.with(context).load(it).into(itemView.MatchImage)
            }

            itemView.MatchName.text = user.userName
            itemView.Matchid.text = user.gender

            itemView.button2.setOnClickListener{
                GlobalScope.launch (Dispatchers.Main){
                    wabiRepository.removeMatch(uid,user.idUser!!)
                    Toast.makeText(context, "Has dejado de ser Waby con ${user.userName}!!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }

    }


}