package com.racoon.waby.ui.auth.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private val viewModel by viewModels<SplashViewModel>()
    //ViewBiding
    private  var _binding: FragmentSplashBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.finishLD.observe(viewLifecycleOwner) { isUserLogged->
            if (isUserLogged) {
                openHome(view)
                //findNavController().navigate(R.id.action_splashFragment_to_registerUserImagesFragment)

            } else {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.resume()
    }

    private fun openHome(view: View) {
        //findNavController().navigate(R.id.action_loginFragment_to_registerUserFragment)
        viewModel.gotoHome(view)
    }

}