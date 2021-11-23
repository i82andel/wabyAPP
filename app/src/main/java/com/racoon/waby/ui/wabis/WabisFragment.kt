package com.racoon.waby.ui.wabis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.racoon.waby.databinding.FragmentWabisBinding

class WabisFragment : Fragment() {

    private lateinit var wabisViewModel: WabisViewModel
    private var _binding: FragmentWabisBinding? = null

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

        val textView: TextView = binding.textWabis
        wabisViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}