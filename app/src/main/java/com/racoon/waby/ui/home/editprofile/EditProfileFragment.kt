package com.racoon.waby.ui.home.editprofile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
    private var TAGS = listOf<String>("")
    private var DESCRIPTION = ""


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
        DESCRIPTION = arguments?.get("description") as String
        TAGS = arguments?.getStringArrayList("tags")!!
        loadUser()

        binding.buttonSave.setOnClickListener {
            editProfile()
            findNavController().navigate(R.id.action_editProfileFragment_to_mainHomeFragment2)
        }

    }

    private fun loadImage(){
        val media = arguments?.getString("image")
        val storageReference = FirebaseStorage.getInstance()
        val gsReference = storageReference.getReferenceFromUrl(media!!)
        gsReference.downloadUrl.addOnSuccessListener {
            Glide.with(requireContext()).load(it).into(binding.imageProfile)
        }
    }

    private fun loadUser(){
        binding.textName.setText(NAME)
        binding.textSurname.setText(SURNAME)
        binding.textDescription.setText(DESCRIPTION)
        binding.textUsername.setText(USERNAME)
        loadTags()
        loadImage()
    }

    private fun loadTags(){

        for(item in TAGS){
            if (item == binding.chip1.text){ binding.chip1.isChecked = true}
            if (item == binding.chip2.text){ binding.chip2.isChecked = true}
            if (item == binding.chip3.text){ binding.chip3.isChecked = true}
            if (item == binding.chip4.text){ binding.chip4.isChecked = true}
            if (item == binding.chip5.text){ binding.chip5.isChecked = true}
            if (item == binding.chip6.text){ binding.chip6.isChecked = true}
            if (item == binding.chip7.text){ binding.chip7.isChecked = true}
            if (item == binding.chip8.text){ binding.chip8.isChecked = true}
        }
    }

    private fun editProfile(){

        val userId = Firebase.auth.currentUser?.uid.toString()

        val db = Firebase.firestore
        db.collection("User")
            .document(userId)
            .update(
                hashMapOf(
                    "name" to binding.textName.text.toString(),
                    "surname" to binding.textSurname.text.toString(),
                    "username" to binding.textUsername.text.toString(),
                    "description" to binding.textDescription.text.toString(),
                    "tags" to (getTags()) as ArrayList<String>)

                        as Map<String, Any>
            )
            .addOnSuccessListener {
                Toast.makeText(context, "Datos actualizados", Toast.LENGTH_SHORT)
                    .show()

            }.addOnFailureListener {

                Toast.makeText(context, R.string.firestore_upload_failure, Toast.LENGTH_SHORT)
                    .show()

            }
    }

    private fun getTags() : List<String>{
        var tags = listOf<String>()

        if ( binding.chip1.isChecked == true){ tags += binding.chip1.text.toString()}
        if ( binding.chip2.isChecked == true){ tags += binding.chip2.text.toString()}
        if ( binding.chip3.isChecked == true){ tags += binding.chip3.text.toString()}
        if ( binding.chip4.isChecked == true){ tags += binding.chip4.text.toString()}
        if ( binding.chip5.isChecked == true){ tags += binding.chip5.text.toString()}
        if ( binding.chip6.isChecked == true){ tags += binding.chip6.text.toString()}
        if ( binding.chip7.isChecked == true){ tags += binding.chip7.text.toString()}
        if ( binding.chip8.isChecked == true){ tags += binding.chip8.text.toString()}


        return tags
    }

}