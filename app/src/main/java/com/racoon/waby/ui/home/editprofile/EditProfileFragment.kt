package com.racoon.waby.ui.home.editprofile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.racoon.waby.R
import com.racoon.waby.data.repository.UserRepositoryImp
import com.racoon.waby.databinding.FragmentEditProfileBinding
import com.racoon.waby.databinding.FragmentProfileBinding
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCaseImpl
import com.racoon.waby.ui.auth.login.MyProfileVMFactory
import com.racoon.waby.ui.home.myprofile.MyProfileViewModel

class EditProfileFragment : Fragment() {

    //ViewBiding
    private  var _binding: FragmentEditProfileBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    //variables del binding
    private var NAME = ""
    private var SURNAME = ""
    private var IMAGE = ""
    private var USERNAME = ""
    private var TAGS = ""

    //ViewModel
    private val viewModel by viewModels<EditProfileViewModel> {
        MyProfileVMFactory(AuthUserUseCaseImpl(UserRepositoryImp()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NAME = arguments?.get("name") as String
        SURNAME = arguments?.get("surname") as String
        IMAGE = arguments?.get("image") as String
        USERNAME = arguments?.get("username") as String
        loadImage()

    }

    private fun loadImage(){
        val media = arguments?.getString("image")
        val storageReference = FirebaseStorage.getInstance()
        val gsReference = storageReference.getReferenceFromUrl(media!!)
        gsReference.downloadUrl.addOnSuccessListener {
            Glide.with(requireContext()).load(it).into(binding.imageProfile)
        }
    }

}