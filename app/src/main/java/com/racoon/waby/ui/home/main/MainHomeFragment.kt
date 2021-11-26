package com.racoon.waby.ui.home.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentMainHomeBinding
import com.racoon.waby.ui.home.map.MapActivity


class MainHomeFragment : Fragment() {

    //ViewBiding
    private  var _binding: FragmentMainHomeBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    //viewModel
    private val viewModel by viewModels<MainHomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myProfileButton.setOnClickListener {
            gotoMyProfile()
        }

        binding.mapButton.setOnClickListener{
            startActivity(Intent(context, MapActivity::class.java))
        }
    }

    private fun gotoMyProfile() {
        findNavController().navigate(R.id.action_mainHomeFragment_to_profileFragment)
    }
}