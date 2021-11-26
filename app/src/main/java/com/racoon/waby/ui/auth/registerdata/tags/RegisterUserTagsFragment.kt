package com.racoon.waby.ui.auth.registerdata.tags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentRegisterUserTagsBinding


class RegisterUserTagsFragment : Fragment() {

    private var NAME = "name"
    private var SURNAME = "surname"
    private var USERNAME = "username"
    private var GENDER = "gender"
    private var DAY = 0
    private var MONTH = 0
    private var YEAR = 0


    //ViewBiding
    private  var _binding: FragmentRegisterUserTagsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = arguments?.getString("name")
        val surname = arguments?.getString("surname")
        val username = arguments?.getString("username")
        val gender = arguments?.getString("gender")

        NAME = name!!
        SURNAME = surname!!
        USERNAME = username!!
        GENDER = gender!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterUserTagsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }





    private fun goNext() {
        val bundle = bundleOf(
            "name" to NAME,
            "surname" to SURNAME,
            "username" to USERNAME,
            "gender" to GENDER,
            "day" to DAY,
            "month" to MONTH,
            "year" to YEAR
        )
        findNavController().navigate(
            R.id.action_registerUserTagsFragment_to_registerUserImagesFragment,
            bundle)

    }
}