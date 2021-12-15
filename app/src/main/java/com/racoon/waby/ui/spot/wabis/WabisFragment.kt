package com.racoon.waby.ui.spot.wabis

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.racoon.waby.databinding.FragmentWabisBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class WabisFragment : Fragment(){

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
            binding.wabisNumber.text = wabisViewModel.getUser().matches?.size.toString()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.loadAnimationWabis.visibility = VISIBLE
        println("hola")
        adapter = WabisAdapter(requireContext())
        binding.loadAnimationWabis.visibility = INVISIBLE
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter


    }


    suspend fun observeData(){
        WabisViewModel().getMatchList().observe(viewLifecycleOwner, Observer {
            adapter.setWabiList(it)
            adapter.notifyDataSetChanged()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}