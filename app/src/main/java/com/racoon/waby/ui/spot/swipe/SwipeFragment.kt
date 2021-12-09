package com.racoon.waby.ui.spot.swipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentSwipeBinding

class SwipeFragment : Fragment(){

    private lateinit var swipeViewModel: SwipeViewModel
    private var _binding: FragmentSwipeBinding? = null
    private var arrayAdapter: arrayAdapter? = null
    private var rowItems: MutableList<cards>? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        swipeViewModel =
            ViewModelProvider(this).get(SwipeViewModel::class.java)

        _binding = FragmentSwipeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var card_1 = cards("crid","manuel","https://i.pinimg.com/474x/d3/65/50/d36550ea1b17394f63c2dff243fe636d.jpg")
        var card_2 = cards("au","adios","https://i.pinimg.com/474x/d3/65/50/d36550ea1b17394f63c2dff243fe636d.jpg")

        rowItems = arrayListOf(card_1,card_2)

        arrayAdapter = arrayAdapter(context, R.layout.item, rowItems)
        binding.frame.adapter = arrayAdapter

        binding.frame.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
            override fun removeFirstObjectInAdapter() {
                Log.d("LIST", "removed object!")
                rowItems?.removeAt(0)
                arrayAdapter!!.notifyDataSetChanged()
            }

            override fun onLeftCardExit(dataObject: Any) {
                val obj = dataObject as cards
                val userId = obj.userId
                //usersDb?.child(userId)?.child("connections")?.child("nope")?.child(currentUId)?.setValue(true)
                Toast.makeText(requireContext(), "Left", Toast.LENGTH_SHORT).show()
            }

            override fun onRightCardExit(dataObject: Any) {
                val obj = dataObject as cards
                val userId = obj.userId
                //usersDb?.child(userId)?.child("connections")?.child("yeps")?.child(currentUId)?.setValue(true)
                //isConnectionMatch(userId)
                Toast.makeText(requireContext(), "Right", Toast.LENGTH_SHORT).show()
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {}
            override fun onScroll(scrollProgressPercent: Float) {}
        })

        binding.likeButton.setOnClickListener{
            rowItems?.removeAt(0)
            arrayAdapter!!.notifyDataSetChanged()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}