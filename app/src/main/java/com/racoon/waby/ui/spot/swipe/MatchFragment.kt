package com.racoon.waby.ui.spot.swipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentMatchBinding
import com.racoon.waby.databinding.FragmentSwipeBinding


class MatchFragment : Fragment() {

    private var _binding: FragmentMatchBinding? = null
    private val binding get() = _binding!!
    private lateinit var image : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        image = arguments?.getString("image").toString()

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentMatchBinding.inflate(inflater, container, false)



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext()).load(image).into(binding.imageMatch)
        Glide.with(requireContext()).load(image).into(binding.imageCircle)

        binding.KeepSwiping.setOnClickListener {
            findNavController().navigate(R.id.action_matchFragment_to_navigation_swipe)
        }
        
        binding.SendMessageButon.setOnClickListener {
            findNavController().navigate(R.id.action_matchFragment_to_navigation_notifications)
        }

    }

}