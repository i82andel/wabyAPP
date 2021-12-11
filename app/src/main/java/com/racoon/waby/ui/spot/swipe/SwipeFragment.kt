package com.racoon.waby.ui.spot.swipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.racoon.waby.R
import com.racoon.waby.data.model.User
import com.racoon.waby.databinding.FragmentSwipeBinding
import com.racoon.waby.ui.spot.wabis.WabisViewModel
import kotlinx.coroutines.*

class SwipeFragment : Fragment() {

    private val swipeViewModel by viewModels<SwipeViewModel>()
    private var _binding: FragmentSwipeBinding? = null
    private var arrayAdapter: arrayAdapter? = null
    private lateinit var user : User
    private var auxiliar: MutableList<String>? = null
    private var dataList = mutableListOf<com.racoon.waby.data.model.User>()

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

        GlobalScope.launch(Dispatchers.Main) {

            user = swipeViewModel.getUser()
            dataList = swipeViewModel.getUsersFromSpot()
            println("datalist en fragment: ${dataList}")

            arrayAdapter = arrayAdapter(context, R.layout.item, dataList)
            binding.frame.adapter = arrayAdapter
            arrayAdapter!!.notifyDataSetChanged()
            binding.frame.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
                override fun removeFirstObjectInAdapter() {
                    Log.d("LIST", "removed object!")
                    dataList?.removeAt(0)
                    arrayAdapter!!.notifyDataSetChanged()
                }

                override fun onLeftCardExit(dataObject: Any) {
                    val obj = dataObject as User
                    //usersDb?.child(userId)?.child("connections")?.child("nope")?.child(currentUId)?.setValue(true)
                    Toast.makeText(requireContext(), "Left", Toast.LENGTH_SHORT).show()
                }

                override fun onRightCardExit(dataObject: Any) {
                    val obj = dataObject as User
                    GlobalScope.launch (Dispatchers.Main){
                       val wabiMatch =  swipeViewModel.makeWabi(user.idUser, obj.idUser)
                        if(wabiMatch == true) {
                            Toast.makeText(requireContext(), "Nuevo Match!!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                }

                override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {}
                override fun onScroll(scrollProgressPercent: Float) {}
            })
            binding.likeButton.setOnClickListener {
                arrayAdapter!!.notifyDataSetChanged()
            }



        }

    }

    /*
    private fun getCurrentUsers() {

        val mutableList = MutableLiveData<MutableList<String>>()

        val db = Firebase.firestore
        val docRef = db.collection("Spot").document("zdzillZYB1nVzTpak2Lz")
        docRef.get()
            .addOnSuccessListener { document ->
                usersList = document.data?.get("assistants") as ArrayList<String>
                println(usersList)
            }

        println("holaaaaaaaaaa")

    }*/
    /*
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


    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}