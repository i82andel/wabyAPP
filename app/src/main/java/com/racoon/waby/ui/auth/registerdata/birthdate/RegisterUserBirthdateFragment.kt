package com.racoon.waby.ui.auth.registerdata.birthdate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.racoon.waby.databinding.FragmentRegisterUserBirthdateBinding



class RegisterUserBirthdateFragment : Fragment() {

    //ViewBiding
    private  var _binding: FragmentRegisterUserBirthdateBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterUserBirthdateBinding.inflate(inflater,container,false)
        return binding.root
    }


}