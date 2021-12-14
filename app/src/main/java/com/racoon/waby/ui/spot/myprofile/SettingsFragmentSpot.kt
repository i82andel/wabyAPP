package com.racoon.waby.ui.spot.myprofile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.racoon.waby.data.model.User
import com.racoon.waby.databinding.FragmentSettingsBinding
import com.racoon.waby.databinding.FragmentSettingsSpotBinding
import com.racoon.waby.ui.home.settings.SettingsViewModel

class SettingsFragmentSpot : Fragment() {

    //ViewBiding
    private  var _binding: FragmentSettingsSpotBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel by viewModels<SettingsFragmentSpotViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsSpotBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Recupera variables del bundle
        val name = arguments?.getString("name")
        val username = arguments?.getString("username")
        loadImage()



        binding.name.setText(name)
        binding.username.setText(username)
        binding.logOutButton.setOnClickListener {
            gotoAuth(view)
        }
    }

    private fun loadImage(){
        val media = arguments?.getString("image")
        val storageReference = FirebaseStorage.getInstance()
        val gsReference = storageReference.getReferenceFromUrl(media!!)
        gsReference.downloadUrl.addOnSuccessListener {
            Glide.with(requireContext()).load(it).into(binding.profileImage)
        }
    }

    private fun gotoAuth(view: View) {
        viewModel.logOut(view)
    }

}