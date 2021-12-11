package com.racoon.waby.ui.spot.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.racoon.waby.R
import com.racoon.waby.databinding.ActivityChatBinding
import com.racoon.waby.ui.spot.wabis.WabisAdapter
import com.racoon.waby.ui.spot.wabis.WabisViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.*
import io.getstream.chat.android.livedata.ChatDomain
import io.getstream.chat.android.ui.channel.list.header.viewmodel.ChannelListHeaderViewModel
import io.getstream.chat.android.ui.channel.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ChatActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityChatBinding
    private val viewModel by viewModels<WabisViewModel>()
    val idUser = Firebase.auth.currentUser?.uid.toString()
    private val client = ChatClient.instance()
    private lateinit var adapter: WabisAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Step 0 - inflate binding
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("estoy en el chatactivity")


        GlobalScope.launch(Dispatchers.Main) {
            val firebaseUser = viewModel.getUser()

            //observeData()

            val user = User(id = firebaseUser.name!!).apply {
                name = firebaseUser.name
                image = firebaseUser.images
            }
            val token = client.devToken(user.id)
            println("id = ${firebaseUser.name}\n token = $token")

            client.connectUser(
                user = user,
                token = token
            ).enqueue()

            //creo el channel pa probar

            val micompa = "dNTvHHKTKZPZUeU2bmkbNImz7Rs2"

            client.createChannel(
                channelType = "messaging",
                members = listOf(idUser,micompa)
            ).enqueue { result ->
                if (result.isSuccess) {
                    val channel = result.data()
                } else {
                    // Handle result.error()
                }
            }



            // Step 3 - Set the channel list filter and order
            // This can be read as requiring only channels whose "type" is "messaging" AND
            // whose "members" include our "user.id"
            val filter = Filters.and(
                Filters.eq("type", "messaging"),
                Filters.`in`("members", listOf(user.id))
            )
            val viewModelFactory = ChannelListViewModelFactory(filter, ChannelListViewModel.DEFAULT_SORT)
            val viewModel: ChannelListViewModel by viewModels { viewModelFactory }
            val listHeaderViewModel: ChannelListHeaderViewModel by viewModels()

            // Step 4 - Connect the ChannelListViewModel to the ChannelListView, loose
            //          coupling makes it easy to customize
            listHeaderViewModel.bindView(binding.channelListHeaderView, this@ChatActivity)
            viewModel.bindView(binding.channelListView, this@ChatActivity)
            binding.channelListView.setChannelItemClickListener { channel ->
                startActivity(ChannelActivity.newIntent(this@ChatActivity, channel))
            }
        }


        /*// Step 1 - Set up the client for API calls and the domain for offline storage
        val client = ChatClient.Builder("b67pax5b2wdq", applicationContext)
            .logLevel(ChatLogLevel.ALL) // Set to NOTHING in prod
            .build()
        ChatDomain.Builder(client, applicationContext).build()        // Step 2 - Authenticate and connect the user
        val user = User(id = "tutorial-droid").apply {
            name = "Tutorial Droid"
            image = "https://bit.ly/2TIt8NR"
        }
        */


    }

    /*suspend fun observeData(){
        WabisViewModel().getWabisList().observe(this, Observer {
            adapter.setWabiList(it)
            adapter.notifyDataSetChanged()
        })
    }*/
}