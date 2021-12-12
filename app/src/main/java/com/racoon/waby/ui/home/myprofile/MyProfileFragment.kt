package com.racoon.waby.ui.home.myprofile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.racoon.waby.R
import com.racoon.waby.data.model.Tag
import com.racoon.waby.data.repository.UserRepositoryImp
import com.racoon.waby.databinding.FragmentProfileBinding
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCaseImpl
import com.racoon.waby.ui.auth.login.LoginVMFactory
import com.racoon.waby.ui.auth.login.LoginViewModel
import com.racoon.waby.ui.auth.login.MyProfileVMFactory
import com.racoon.waby.ui.auth.signup.SignUpVMFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.spot_item.view.*
import kotlinx.android.synthetic.main.wabi_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL
import java.time.Instant
import java.time.ZoneId
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
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
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

        tags = user.tags!!
        initRecycler()
        //Asigna valores al bundle
        NAME = user.name.toString()
        SURNAME = user.surname.toString()
        USERNAME = user.userName.toString()
        DESCRIPTION = user.description.toString()
        EMAIL = user.email.toString()
        IMAGE = media
        TAGS = tags

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
        findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment, bundle)
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
        findNavController().navigate(R.id.action_profileFragment_to_settingsFragment, bundle)
    }

    //Inicia el RecyclerView para mostrar Tags
    private fun initRecycler(){
        TagsList.layoutManager = LinearLayoutManager(context)
        //TagsList.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        val adapter = TagAdapter(tags)
        TagsList.adapter = adapter
    }
}