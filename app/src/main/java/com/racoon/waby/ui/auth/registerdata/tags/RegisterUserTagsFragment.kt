package com.racoon.waby.ui.auth.registerdata.tags

import android.graphics.Insets.add
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
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
    private var TAGS = ArrayList<String>()


    //ViewBiding
    private var _binding: FragmentRegisterUserTagsBinding? = null

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

        NAME = name!!
        SURNAME = surname!!
        USERNAME = username!!
        GENDER = gender!!
        DAY = day!!
        MONTH = month!!
        YEAR = year!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterUserTagsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()

        binding.nextButton.setOnClickListener {
            if (TAGS.size >=3 && TAGS.size <= 5) {
                goNext()
            } else if (TAGS.size < 3) {
                TAGS.clear()
                clearChips()
                Toast.makeText(context, R.string.register_tags_error2, Toast.LENGTH_LONG).show()

            } else if (TAGS.size > 5) {
                TAGS.clear()
                clearChips()
                Toast.makeText(context, R.string.register_tags_error, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun setUp() {
        binding.chip1.setOnClickListener {
            val chip = binding.chip1.text.toString()
            if (binding.chip1.isChecked) {
                TAGS.add(chip)
            }else {
                TAGS.remove(chip)
            }

        }
        binding.chip2.setOnClickListener {
            val chip = binding.chip2.text.toString()
            if (binding.chip2.isChecked) {
                TAGS.add(chip)
            }else {
                TAGS.remove(chip)
            }
        }
        binding.chip3.setOnClickListener {
            val chip = binding.chip3.text.toString()
            if (binding.chip3.isChecked) {
                TAGS.add(chip)
            }else {
                TAGS.remove(chip)
            }
        }
        binding.chip4.setOnClickListener {
            val chip = binding.chip4.text.toString()
            if (binding.chip4.isChecked) {
                TAGS.add(chip)
            }else {
                TAGS.remove(chip)
            }
        }
        binding.chip5.setOnClickListener {
            val chip = binding.chip5.text.toString()
            if (binding.chip5.isChecked) {
                TAGS.add(chip)
            }else {
                TAGS.remove(chip)
            }
        }
        binding.chip6.setOnClickListener {
            val chip = binding.chip6.text.toString()
            if (binding.chip6.isChecked) {
                TAGS.add(chip)
            }else {
                TAGS.remove(chip)
            }
        }
        binding.chip7.setOnClickListener {
            val chip = binding.chip7.text.toString()
            if (binding.chip7.isChecked) {
                TAGS.add(chip)
            }else {
                TAGS.remove(chip)
            }
        }
        binding.chip8.setOnClickListener {
            val chip = binding.chip8.text.toString()
            if (binding.chip8.isChecked) {
                TAGS.add(chip)
            }else {
                TAGS.remove(chip)
            }
        }


    }

    private fun clearChips() {
        binding.chip1.isChecked = false
        binding.chip2.isChecked = false
        binding.chip3.isChecked = false
        binding.chip4.isChecked = false
        binding.chip5.isChecked = false
        binding.chip6.isChecked = false
        binding.chip7.isChecked = false
        binding.chip8.isChecked = false
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
            "tags" to TAGS
        )
        findNavController().navigate(
            R.id.action_registerUserTagsFragment_to_registerUserImagesFragment,
            bundle)


    }
}