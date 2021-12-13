package com.racoon.waby.ui.spot.wabis

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.racoon.waby.databinding.FragmentSpotHomeBinding
import com.racoon.waby.databinding.FragmentWabisBinding
import com.racoon.waby.ui.spot.chat.ChannelActivity
import com.racoon.waby.ui.spot.spothome.MySpotAdapter
import com.racoon.waby.ui.spot.spothome.SpotHomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class WabisFragment : Fragment() {

    private val wabisViewModel by viewModels<WabisViewModel>()
    private var _binding: FragmentWabisBinding? = null
    private lateinit var adapter: WabisAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWabisBinding.inflate(inflater, container, false)
        GlobalScope.launch (Dispatchers.Main){
            observeData()
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("hola")
        adapter = WabisAdapter(requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

    }


    suspend fun observeData(){
        WabisViewModel().getWabisList().observe(viewLifecycleOwner, Observer {
            adapter.setWabiList(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}