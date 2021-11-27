package com.racoon.waby.ui.auth.registerdata.description

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentRegisterUserDescriptionBinding


class RegisterUserDescriptionFragment : Fragment() {

    private var NAME = "name"
    private var SURNAME = "surname"
    private var USERNAME = "username"
    private var GENDER = "gender"
    private var DAY = 0
    private var MONTH = 0
    private var YEAR = 0
    private var TAGS = ArrayList<String>()
    private var IMAGES = ArrayList<Uri>()

    //ViewBiding
    private  var _binding: FragmentRegisterUserDescriptionBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val name = arguments?.getString("name")
        val surname = arguments?.getString("surname")
        val username = arguments?.getString("username")
        val gender = arguments?.getString("gender")
        val day = arguments?.getInt("day")
        val month = arguments?.getInt("month")
        val year = arguments?.getInt("year")
        val tags = arguments?.getStringArrayList("tags")

        println(name)
        println(surname)
        println(username)
        println(gender)
        println(day)
        println(month)
        println(year)
        println(tags)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterUserDescriptionBinding.inflate(inflater,container,false)
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
            "year" to YEAR,
            "tags" to TAGS,
            "uris" to IMAGES
        )
        findNavController().navigate(
            R.id.action_registerUserImagesFragment_to_registerUserDescriptionFragment,
            bundle)


    }

}