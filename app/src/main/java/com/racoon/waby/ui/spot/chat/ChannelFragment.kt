package com.racoon.waby.ui.spot.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.racoon.waby.R
import com.racoon.waby.databinding.FragmentChannelBinding
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.livedata.ChatDomain
import com.racoon.waby.data.model.User
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.models.image
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory

class ChannelFragment : Fragment() {

    private  var _binding: FragmentChannelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChannelBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val client = setUpClient()
        val user = authenticateUser()
        connectUser(user,client)

        //filters
        val filter = Filters.and(
            Filters.eq("type","messaging"),
            Filters.`in`("members", listOf(user.id))
        )
        val viewModelFactory = ChannelListViewModelFactory(filter, ChannelListViewModel.DEFAULT_SORT)
        val viewModel: ChannelListViewModel by viewModels {viewModelFactory}

        viewModel.bindView(binding.channelListView, this)
        binding.channelListView.setChannelItemClickListener{ channel ->

        }

    }

    private fun setUpClient(): ChatClient{
        val client =
            ChatClient.Builder(getString(R.string.api_key), requireContext()).logLevel(ChatLogLevel.ALL).build()
        ChatDomain.Builder(client, requireContext()).build()
        return client
    }

    private fun authenticateUser(): io.getstream.chat.android.client.models.User {
        val user = io.getstream.chat.android.client.models.User(id = "tutorial-droid")
            .apply{
                name = "Tutorial Droid"
                image = "https://bit.ly/2TIt8NR"
            }
        return user
    }

    private fun connectUser(user: io.getstream.chat.android.client.models.User, client: ChatClient){
        client.connectUser(
            user = user,
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoidHV0b3JpYWwtZHJvaWQifQ.NhEr0hP9W9nwqV7ZkdShxvi02C5PR7SJE7Cs4y7kyqg"
        ).enqueue()
    }

}