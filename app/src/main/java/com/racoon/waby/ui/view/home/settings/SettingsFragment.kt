package com.racoon.waby.ui.view.home.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentLoginBinding
import com.racoon.waby.databinding.FragmentMainHomeBinding
import com.racoon.waby.databinding.FragmentSettingsBinding
import com.racoon.waby.ui.view.auth.AuthActivity
import com.racoon.waby.ui.viewmodel.home.settings.SettingsViewModel

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
        binding.logOutButton.setOnClickListener {
            gotoAuth(view)
        }
    }

    private fun gotoAuth(view: View) {
            viewModel.logOut(view)
    }

}