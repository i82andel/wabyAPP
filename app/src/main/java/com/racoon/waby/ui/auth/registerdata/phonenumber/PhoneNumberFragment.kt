package com.racoon.waby.ui.auth.registerdata.phonenumber

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentPhoneNumberBinding
import com.racoon.waby.databinding.FragmentRegisterUserBirthdateBinding
import java.util.concurrent.TimeUnit


class PhoneNumberFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    //ViewBiding
    private  var _binding: FragmentPhoneNumberBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPhoneNumberBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSend.setOnClickListener {
            var phoneNumber = binding.etPhone.text.toString()
            if (phoneNumber.isEmpty()) {
                Toast.makeText(context,R.string.phonenumber_empty_error,Toast.LENGTH_SHORT).show()
            }else if (phoneNumber.length != 9){
                Toast.makeText(context,R.string.phonenumber_invalid_error,Toast.LENGTH_SHORT).show()
            }else {
                phoneNumber = "+34$phoneNumber"
                openSend(phoneNumber)
            }
        }

    }

    private fun openSend(phoneNumber: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSend.visibility = View.INVISIBLE

        var mCallbacks = object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}
            override fun onVerificationFailed(e: FirebaseException) {
                binding.progressBar.visibility = View.GONE
                binding.btnSend.visibility = View.VISIBLE
                Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: ForceResendingToken,
            ) {
                binding.progressBar.visibility = View.GONE
                binding.btnSend.visibility = View.VISIBLE
                Toast.makeText(context,
                    "OTP is successfully send.",
                    Toast.LENGTH_SHORT).show()

                val bundle = bundleOf(
                    "phone" to binding.etPhone.text.toString().trim(),
                    "verificationId" to verificationId
                )

                findNavController().navigate(R.id.action_phoneNumberFragment_to_phoneNumberVerifyFragment,bundle)

            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(mCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}