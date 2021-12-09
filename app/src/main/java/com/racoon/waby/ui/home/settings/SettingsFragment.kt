package com.racoon.waby.ui.home.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.racoon.waby.data.model.User
import com.racoon.waby.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    //ViewBiding
    private  var _binding: FragmentSettingsBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    //ViewModel
    private val viewModel by viewModels<SettingsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Recupera variables del bundle
        val name = arguments?.getString("name")
        val username = arguments?.getString("username")


        binding.name.setText(name)
        binding.username.setText(username)
        binding.logOutButton.setOnClickListener {
            gotoAuth(view)
        }
    }

    private fun gotoAuth(view: View) {
            viewModel.logOut(view)
    }

}