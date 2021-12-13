package com.racoon.waby.ui.home.wabisHome

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentChannelBinding
import com.racoon.waby.ui.spot.chat.ChannelActivity
import com.racoon.waby.ui.spot.wabis.WabisViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.*
import io.getstream.chat.android.livedata.ChatDomain
import io.getstream.chat.android.ui.avatar.AvatarView
import io.getstream.chat.android.ui.channel.list.header.viewmodel.ChannelListHeaderViewModel
import io.getstream.chat.android.ui.channel.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory
import kotlinx.android.synthetic.main.fragment_login_admin_user.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChannelFragment : Fragment() {

    private var _binding: FragmentChannelBinding? = null
    private val binding get() = _binding!!

    private val client = ChatClient.instance()
    private val viewModel by viewModels<WabisViewModel>()
    val idUser = Firebase.auth.currentUser?.uid.toString()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChannelBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aa = viewLifecycleOwner

        GlobalScope.launch(Dispatchers.Main) {
            val firebaseUser = viewModel.getUser()
            println(firebaseUser.name)
            val user = User(id = firebaseUser.userName!!).apply {
                name = firebaseUser.userName
                image = firebaseUser.images
            }
            val token = client.devToken(user.id)
            println("id = ${firebaseUser.name}\n token = $token")

            val filter = Filters.and(
                Filters.eq("type", "messaging"),
                Filters.`in`("members", listOf(user.id))
            )
            val viewModelFactory = ChannelListViewModelFactory(filter, ChannelListViewModel.DEFAULT_SORT)
            val viewModel: ChannelListViewModel by viewModels { viewModelFactory }
            val listHeaderViewModel: ChannelListHeaderViewModel by viewModels()

            listHeaderViewModel.bindView(binding.channelListHeaderView, aa)
            viewModel.bindView(binding.channelsView, aa)
            binding.channelsView.setChannelItemClickListener { channel ->
                startActivity(ChannelActivity.newIntent(requireContext(), channel))
            }

            binding.channelListHeaderView.setOnActionButtonClickListener{

            }

            binding.channelsView.setChannelDeleteClickListener { channel ->
                deleteChannel(channel)
            }
        }



    }

    private fun deleteChannel(channel: Channel) {
        ChatDomain.instance().deleteChannel(channel.cid).enqueue { result ->
            if (result.isSuccess) {
                showToast("Channel: ${channel.name} removed!")
            } else {
                Log.e("ChannelFragment", result.error().message.toString())
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}