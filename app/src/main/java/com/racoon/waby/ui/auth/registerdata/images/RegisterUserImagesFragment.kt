package com.racoon.waby.ui.auth.registerdata.images

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentRegisterUserImagesBinding


class RegisterUserImagesFragment : Fragment() {

    private val SELECT_ACTIVITY = 50
    private var imageUri: Uri? = null

    private var NAME = "name"
    private var SURNAME = "surname"
    private var USERNAME = "username"
    private var GENDER = "gender"
    private var DAY = 0
    private var MONTH = 0
    private var YEAR = 0
    private var TAGS = ArrayList<String>()
    private var IMAGES = ArrayList<String>()

    //ViewBiding
    private  var _binding: FragmentRegisterUserImagesBinding? = null
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
        _binding = FragmentRegisterUserImagesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()

        binding.nextButton.setOnClickListener {
            goNext()
        }
    }

    private fun setUp() {
        binding.imageView1.setOnClickListener {
            activity?.let { it1 ->
                ImageController.selectPhotoFromGallery(it1,SELECT_ACTIVITY)
            }
        }

        binding.imageView2.setOnClickListener {
            activity?.let { it1 ->
                ImageController.selectPhotoFromGallery(it1,SELECT_ACTIVITY)
            }
        }

        binding.imageView3.setOnClickListener {
            activity?.let { it1 ->
                ImageController.selectPhotoFromGallery(it1,SELECT_ACTIVITY)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                imageUri = data!!.data

                binding.imageView1.setImageURI(imageUri)





            }
        }
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

        )
        findNavController().navigate(
            R.id.action_registerUserImagesFragment_to_registerUserDescriptionFragment,
            bundle)


    }

}