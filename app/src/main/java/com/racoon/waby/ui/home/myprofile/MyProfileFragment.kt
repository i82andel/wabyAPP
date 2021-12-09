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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.racoon.waby.R
import com.racoon.waby.data.model.Tag
import com.racoon.waby.data.repository.UserRepositoryImp
import com.racoon.waby.databinding.FragmentProfileBinding
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCaseImpl
import com.racoon.waby.ui.auth.login.LoginVMFactory
import com.racoon.waby.ui.auth.login.LoginViewModel
import com.racoon.waby.ui.auth.login.MyProfileVMFactory
import com.racoon.waby.ui.auth.signup.SignUpVMFactory
import kotlinx.android.synthetic.main.fragment_profile.*
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

    var tags : List<Tag> = listOf(
        //Tag("01", "Futbol"),
        //Tag("02", "Musica"),
        //Tag("03", "Pintar")
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        setCurrentUser()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()



        binding.settingsButton.setOnClickListener {
            gotoSettings()
        }

    }

    private fun setCurrentUser(){
        //user = viewModel.getCurrentUser()
        val uid = "7dscT5MXidZQjtL51MPQRi5ZdK62"
        val db = Firebase.firestore
        val userList = db.collection("User")


        userList.document(uid).get().addOnSuccessListener { document ->
            if (document.exists()) {
                binding.nameText.setText(document.get("name") as String?)
                binding.DescriptionText.setText(document.get("description") as String?)
                binding.emailText.setText(document.get("email") as String?)
                binding.usernameText.setText(document.get("username") as String?)
                binding.textBD.setText(dateToString(document.get("birthdate") as Timestamp))
                binding.textPhone.setText(document.get("phoneNumber") as String?)
                tags = document.get("tags") as List<Tag>
            }
        }
    }

    private fun dateToString(date : Timestamp) : String{
        var Sdate = "01/02/2000"

        return Sdate
    }

    private fun gotoSettings() {
        findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
    }

    private fun initRecycler(){
        TagsList.layoutManager = LinearLayoutManager(context)
        //TagsList.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        val adapter = TagAdapter(tags)
        TagsList.adapter = adapter
    }
}