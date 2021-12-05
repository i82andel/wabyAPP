package com.racoon.waby.ui.home.myprofile

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.auth.User
import com.racoon.waby.R
import com.racoon.waby.data.repository.UserRepositoryImp
import com.racoon.waby.databinding.FragmentProfileBinding
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCaseImpl
import com.racoon.waby.ui.auth.login.LoginVMFactory
import com.racoon.waby.ui.auth.login.LoginViewModel
import com.racoon.waby.ui.auth.login.MyProfileVMFactory
import com.racoon.waby.ui.auth.signup.SignUpVMFactory
import java.time.format.DateTimeFormatter
import java.util.*

class MyProfileFragment : Fragment() {

    //ViewBiding
    private  var _binding: FragmentProfileBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel by viewModels<MyProfileViewModel> {
        MyProfileVMFactory(AuthUserUseCaseImpl(UserRepositoryImp()))
    }
    private var user : com.racoon.waby.data.model.User = com.racoon.waby.data.model.User()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCurrentUser()
        binding.nameText.setText(user.name+ " " + user.surname)
        binding.DescriptionText.setText(user.description)
        binding.emailText.setText(user.email)
        binding.usernameText.setText(user.userName)
        binding.textBD.setText("01/01/2000")
        binding.textPhone.setText(user.phoneNumber)

        binding.settingsButton.setOnClickListener {
            gotoSettings()
        }

    }

    private fun setCurrentUser(){

        user = viewModel.getCurrentUser()

    }

    private fun gotoSettings() {
        findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
    }
}