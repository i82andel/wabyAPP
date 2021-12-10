package com.racoon.waby.ui.spot.swipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentChannelBinding
import com.racoon.waby.databinding.FragmentSwipeBinding
import com.racoon.waby.ui.spot.spothome.MySpotAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SwipeFragment : Fragment() {

    private lateinit var swipeViewModel: SwipeViewModel
    private var _binding: FragmentSwipeBinding? = null
    private var arrayAdapter: arrayAdapter? = null
    private var rowItems: MutableList<Card>? = null
    private var usersList: ArrayList<String>? = null
    private var auxiliar: MutableList<String>? = null
    private var dataList = mutableListOf<Card>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentSwipeBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch {
            getCurrentUsers()
            delay(3_000L)
            getDataUsers(usersList!!)
            delay(3_000L)
            println(dataList[0].name)
            var card_1 = Card("crid",
                "manuel",
                "https://i.pinimg.com/474x/d3/65/50/d36550ea1b17394f63c2dff243fe636d.jpg")
            var card_2 = Card("au",
                "adios",
                "https://i.pinimg.com/474x/d3/65/50/d36550ea1b17394f63c2dff243fe636d.jpg")

            rowItems = arrayListOf(card_1, card_2)
            arrayAdapter = arrayAdapter(context, R.layout.item, rowItems)
        }
        binding.frame.adapter = arrayAdapter
        binding.frame.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
            override fun removeFirstObjectInAdapter() {
                Log.d("LIST", "removed object!")
                rowItems?.removeAt(0)
                arrayAdapter!!.notifyDataSetChanged()
            }

            override fun onLeftCardExit(dataObject: Any) {
                val obj = dataObject as Card
                val userId = obj.userId
                //usersDb?.child(userId)?.child("connections")?.child("nope")?.child(currentUId)?.setValue(true)
                Toast.makeText(requireContext(), "Left", Toast.LENGTH_SHORT).show()
            }

            override fun onRightCardExit(dataObject: Any) {
                val obj = dataObject as Card
                val userId = obj.userId
                //usersDb?.child(userId)?.child("connections")?.child("yeps")?.child(currentUId)?.setValue(true)
                //isConnectionMatch(userId)
                Toast.makeText(requireContext(), "Right", Toast.LENGTH_SHORT).show()
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {}
            override fun onScroll(scrollProgressPercent: Float) {}
        })
        binding.likeButton.setOnClickListener {
            rowItems?.removeAt(0)
            arrayAdapter!!.notifyDataSetChanged()
        }
    }

    private fun getCurrentUsers() {

        val mutableList = MutableLiveData<MutableList<String>>()

        val db = Firebase.firestore
        val docRef = db.collection("Spot").document("zdzillZYB1nVzTpak2Lz")
        docRef.get()
            .addOnSuccessListener { document ->
                usersList = document.data?.get("assistants") as ArrayList<String>
                println(usersList)
            }
        /*.addOnSuccessListener { document ->
            println("nose la vd xd")
            if (document.exists()) {
                val user = document.data?.get("assistants") as ArrayList<String>
                println(user)
                mutableList.value = user
                println("exsite por los muerttos")
            } else {
                println("no existe broi")
            }
        }.addOnFailureListener {
            println("failure")
        }*/

    }

    fun getDataUsers(userIds: ArrayList<String>) {
        val db = Firebase.firestore

        for (i in userIds.indices) {
            val docRef = db.collection("User").document(userIds[i])
                .get().addOnSuccessListener { document ->
                    if (document.exists()) {
                        val name = document.data?.get("name").toString()
                        val description = document.data?.get("description").toString()
                        val image = document.data?.get("images").toString()
                        val tags = document.data?.get("tags") as ArrayList<String>
                        val card = Card(userIds[i], name, image, tags, description)
                        dataList.add(card)
                    }
                }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}