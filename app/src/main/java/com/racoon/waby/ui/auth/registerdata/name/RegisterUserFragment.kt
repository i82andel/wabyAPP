package com.racoon.waby.ui.auth.registerdata.name

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentRegisterUserBinding
import kotlinx.android.synthetic.main.fragment_register_user.*

class RegisterUserFragment : Fragment() {

    private var NAME = "name"
    private var SURNAME = "surname"
    private var USERNAME = "username"

    private val viewModel by viewModels<RegisterUserViewModel>()

    //ViewBiding
    private  var _binding: FragmentRegisterUserBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterUserBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        viewModelSetUp()

    }

    private fun setUp() {
        binding.nextButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val surname = binding.surnameEditText.text.toString()
            val username = binding.userNameEditText.text.toString()

            NAME = name
            USERNAME = username
            SURNAME = surname

            viewModel.setValues(name,surname,username)
        }
    }

    private fun viewModelSetUp() {
        with(viewModel) {

            successLD.observe(viewLifecycleOwner) {
                activity?.also {
                    Toast.makeText(context, R.string.register_success, Toast.LENGTH_SHORT).show()
                }
                goNext()
            }
            errorLD.observe(viewLifecycleOwner) { msg->
                activity?.also {
                    Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goNext() {
        val bundle = bundleOf(
            "name" to NAME,
            "surname" to SURNAME,
            "username" to USERNAME
        )
        findNavController().navigate(
            R.id.action_registerUserFragment_to_registerUserGenderFragment,
            bundle)

    }

}