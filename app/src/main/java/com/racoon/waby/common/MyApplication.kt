package com.racoon.waby.common

import android.app.Application
import com.racoon.waby.R
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.notifications.handler.NotificationConfig
import io.getstream.chat.android.livedata.ChatDomain
import io.getstream.chat.android.pushprovider.firebase.FirebasePushDeviceGenerator

class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        
        val notificationConfig = NotificationConfig(
            pushDeviceGenerators = listOf(FirebasePushDeviceGenerator())
        )

        val client =
            ChatClient.Builder(getString(R.string.api_key), this)
                .logLevel(ChatLogLevel.ALL)
                .notifications(notificationConfig)
                .build()
        ChatDomain.Builder(client, this).build()
    }
}