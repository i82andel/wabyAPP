package com.racoon.waby.ui.spothome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.racoon.waby.databinding.FragmentSpotHomeBinding

class SpotHomeFragment : Fragment() {

    private lateinit var spotHomeViewModel: SpotHomeViewModel
    private var _binding: FragmentSpotHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        spotHomeViewModel =
            ViewModelProvider(this).get(SpotHomeViewModel::class.java)

        _binding = FragmentSpotHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSpotHome
        spotHomeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}