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
import com.racoon.waby.common.MyApplication
import com.racoon.waby.data.model.User
import com.racoon.waby.databinding.FragmentSwipeBinding
import com.racoon.waby.ui.spot.wabis.WabisViewModel
import io.getstream.chat.android.client.ChatClient
import kotlinx.coroutines.*

class SwipeFragment : Fragment() {

    private val swipeViewModel by viewModels<SwipeViewModel>()
    private var _binding: FragmentSwipeBinding? = null
    private var arrayAdapter: arrayAdapter? = null
    private lateinit var user : User
    private var auxiliar: MutableList<String>? = null
    private var dataList = mutableListOf<com.racoon.waby.data.model.User>()
    private val client = ChatClient.instance()
    private lateinit var idSpot: String


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        idSpot = checkNotNull(activity?.intent?.getStringExtra("idSpot"))
    }


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
            dataList = swipeViewModel.getUsersFromSpot(idSpot)
            println("datalist en fragment: ${dataList}")
            dataList = swipeViewModel.getNotSeenUsers(dataList, user.idUser!!)
            println("USUARIOS NO VISTOS HASTA EL MOMENTO: ${dataList}")
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
                    GlobalScope.launch (Dispatchers.Main) {
                        swipeViewModel.addSeenUser(user.idUser, obj.idUser)
                    }
                    Toast.makeText(requireContext(), "Left", Toast.LENGTH_SHORT).show()
                }

                override fun onRightCardExit(dataObject: Any) {
                    val obj = dataObject as User
                    GlobalScope.launch (Dispatchers.Main){
                        swipeViewModel.addSeenUser(user.idUser, obj.idUser)
                        val wabiMatch =  swipeViewModel.makeWabi(user.idUser, obj.idUser)
                        if(wabiMatch == true) {
                            createChat(user.userName!!,obj.userName!!)
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

    fun createChat(currentUserId: String, myFriendId: String) {
        client.createChannel(
            channelType = "messaging",
            members = listOf(currentUserId,myFriendId)
        ).enqueue { result ->
            if (result.isSuccess) {
                val channel = result.data()
            } else {
                println("NO HE PODIDO CREAR EL CHAT")
                // Handle result.error()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}