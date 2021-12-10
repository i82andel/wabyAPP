package com.racoon.waby.ui.spot.wabis

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
import com.racoon.waby.ui.spot.spothome.MySpotAdapter
import com.racoon.waby.ui.spot.spothome.SpotHomeViewModel

class WabisFragment : Fragment() {

    private lateinit var wabisViewModel: WabisViewModel
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
        wabisViewModel =
            ViewModelProvider(this).get(WabisViewModel::class.java)

        _binding = FragmentWabisBinding.inflate(inflater, container, false)
        val root = binding.root

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = WabisAdapter(requireContext())
        binding.userList.layoutManager = LinearLayoutManager(context)
        binding.spotList.adapter = adapter
        observeData()

    }

    fun observeData(){
        WabisViewModel.getWabisList().observe(viewLifecycleOwner, Observer {
            adapter.setSpotList(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}