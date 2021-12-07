package com.racoon.waby.ui.auth.registerdata.phonenumber

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentPhoneNumberBinding
import com.racoon.waby.databinding.FragmentPhoneNumberVerifyBinding

class PhoneNumberVerifyFragment : Fragment() {

    private var PHONENUMBER = "phonenumber"
    private var VERIFICATIONID = "verificationid"

    //ViewBiding
    private var _binding: FragmentPhoneNumberVerifyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val phonenumber = arguments?.getString("phone")
        val verificationId = arguments?.getString("verificationId")

        PHONENUMBER = phonenumber!!
        VERIFICATIONID = verificationId!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPhoneNumberVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextInput()

        binding.tvMobile.setText(String.format(
            "+34-$PHONENUMBER"
        ))

        binding.btnVerify.setOnClickListener {



            binding.progressBarVerify.visibility = View.VISIBLE
            binding.btnVerify.visibility = View.INVISIBLE
            if (binding.etC1.text.toString().trim().isEmpty() ||
                binding.etC2.text.toString().trim().isEmpty() ||
                binding.etC3.text.toString().trim().isEmpty() ||
                binding.etC4.text.toString().trim().isEmpty() ||
                binding.etC5.text.toString().trim().isEmpty() ||
                binding.etC6.text.toString().trim().isEmpty()
            ) {
                Toast.makeText(context, "OTP is not Valid!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (VERIFICATIONID != null) {
                    val code = binding.etC1.text.toString().trim() +
                            binding.etC2.text.toString().trim() +
                            binding.etC3.text.toString().trim() +
                            binding.etC4.text.toString().trim() +
                            binding.etC5.text.toString().trim() +
                            binding.etC6.text.toString().trim()
                    val credential = PhoneAuthProvider.getCredential(VERIFICATIONID, code)
                    FirebaseAuth
                        .getInstance()
                        .signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                binding.progressBarVerify.visibility = View.VISIBLE
                                binding.btnVerify.visibility = View.INVISIBLE
                                Toast.makeText(context,
                                    "Welcome...",
                                    Toast.LENGTH_SHORT)
                                    .show()
                                val bundle = bundleOf(
                                    "phonenumber" to PHONENUMBER
                                )
                               findNavController().navigate(R.id.action_phoneNumberVerifyFragment_to_registerUserFragment,
                               bundle)
                            } else {
                                binding.progressBarVerify.visibility = View.GONE
                                binding.btnVerify.visibility = View.VISIBLE
                                Toast.makeText(context,
                                    "OTP is not Valid!",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }

    }

    private fun editTextInput() {
        binding.etC1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.etC2.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etC2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.etC3.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etC3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.etC4.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etC4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.etC5.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etC5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.etC6.requestFocus()
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }



}