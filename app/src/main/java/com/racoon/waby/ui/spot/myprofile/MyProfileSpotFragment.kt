package com.racoon.waby.ui.spot.myprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.racoon.waby.R
import com.racoon.waby.data.repository.UserRepositoryImp
import com.racoon.waby.databinding.FragmentMyProfileSpotBinding
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCaseImpl
import com.racoon.waby.ui.auth.login.MyProfileVMFactory
import com.racoon.waby.ui.home.myprofile.MyProfileViewModel
import com.racoon.waby.ui.home.myprofile.TagAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyProfileSpotFragment : Fragment() {

    //ViewBiding
    private  var _binding: FragmentMyProfileSpotBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel by viewModels<MyProfileViewModel> {
        MyProfileVMFactory(AuthUserUseCaseImpl(UserRepositoryImp()))
    }

    //Tags de prueba
    var tags : List<String> = listOf(
        ""
    )

    //Variables del bundle
    private var NAME = ""
    private var USERNAME = ""
    private var SURNAME = ""
    private var DESCRIPTION = ""
    private var PHONENUMBER = ""
    private var EMAIL = ""
    private var IMAGE = ""
    private var TAGS = listOf("")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyProfileSpotBinding.inflate(inflater,container,false)
        GlobalScope.launch (Dispatchers.Main){
            setCurrentUser()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.EditButton.setOnClickListener{
            gotoEdit()
        }

        binding.settingsButton.setOnClickListener {
            gotoSettings()
        }
    }

    //Obtiene los datos del usuario actual
    suspend fun setCurrentUser(){
        val user = MyProfileViewModel().getCurrentUser()

        binding.nameText.setText(user.name + " " + user.surname)
        binding.DescriptionText.setText(user.description)
        binding.emailText.setText(user.email)
        binding.usernameText.setText(user.userName)

        val media = arguments?.get("image") as String
        val storageReference = FirebaseStorage.getInstance()
        val gsReference = storageReference.getReferenceFromUrl(media!!)
        gsReference.downloadUrl.addOnSuccessListener {
            Glide.with(requireContext()).load(it).into(binding.ProfileImage)
        }
        val dateString = user.birthdate!!.date.toString() + "/" + (user.birthdate!!.month.toInt()+1) + "/" + user.birthdate!!.year

        binding.textBD.setText(dateString)

        val tags = user.tags
        setTags(tags)
        //Asigna valores al bundle
        NAME = user.name.toString()
        SURNAME = user.surname.toString()
        USERNAME = user.userName.toString()
        DESCRIPTION = user.description.toString()
        EMAIL = user.email.toString()
        IMAGE = media
        TAGS = tags!!

    }


    private fun gotoEdit(){
        val bundle = bundleOf(
            "name" to NAME,
            "surname" to SURNAME,
            "username" to USERNAME,
            "description" to DESCRIPTION,
            "image" to IMAGE,
            "tags" to TAGS
        )
        findNavController().navigate(R.id.action_myProfileSpotFragment_to_editProfileFragment2, bundle)
    }


    //Para ir a Profile Setting. Pasa bundle para recuperar datos
    private fun gotoSettings() {
        val bundle = bundleOf(
            "phoneNumber" to PHONENUMBER,
            "name" to NAME,
            "surname" to SURNAME,
            "username" to USERNAME,
            "description" to DESCRIPTION,
            "email" to EMAIL,
            "image" to IMAGE,
            "tags" to TAGS
        )
        findNavController().navigate(R.id.action_myProfileSpotFragment_to_settingsFragmentSpot, bundle)
    }

    private fun setTags(tags: ArrayList<String>?) {
        var countNumber = 0
        if (tags != null) {
            for (tag in tags) {

                when (countNumber) {

                    0 -> {
                        binding.tag1.text = tag
                        binding.Tag1.visibility = View.VISIBLE
                    }
                    1 -> {
                        binding.tag2.text = tag
                        binding.Tag2.visibility = View.VISIBLE
                    }
                    2 -> {
                        binding.tag3.text = tag
                        binding.Tag3.visibility = View.VISIBLE
                    }
                    3 -> {
                        binding.tag4.text = tag
                        binding.Tag4.visibility = View.VISIBLE
                    }
                    4 -> {
                        binding.tag5.text = tag
                        binding.Tag5.visibility = View.VISIBLE
                    }
                }
                countNumber++
            }
        }
    }

}