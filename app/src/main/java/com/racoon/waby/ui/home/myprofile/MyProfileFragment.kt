package com.racoon.waby.ui.home.myprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.racoon.waby.R
import com.racoon.waby.data.model.Tag
import com.racoon.waby.databinding.FragmentProfileBinding
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCaseImpl
import com.racoon.waby.ui.auth.login.MyProfileVMFactory
import kotlinx.android.synthetic.main.fragment_profile.*
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
        Tag("01", "Futbol"),
        Tag("02", "Musica"),
        Tag("03", "Pintar")
    )


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
        initRecycler()

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

    private fun initRecycler(){
        TagsList.layoutManager = LinearLayoutManager(context)
        //TagsList.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        val adapter = TagAdapter(tags)
        TagsList.adapter = adapter
    }
}