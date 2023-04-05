package com.theideal.goride.utility

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class FirebaseMessageReceiverBroadCast:BroadcastReceiver() {

    override fun onReceive(p0: Context?, intent: Intent?) {
        if (intent!!.action == "com.google.firebase.MESSAGING_EVENT") {
            // Get the message data
            val data = intent.extras?.get("data") as? Map<*, *> ?: return

            // Extract the message contents
            val title = data["title"] as? String
            val body = data["body"] as? String
            val senderId = data["sender_id"] as? String
            val receiverId = data["receiver_id"] as? String
            val messageId = data["message_id"] as? String

            // Process the message as needed
            Timber.i("messageId: $messageId")
            Timber.i("receiverId: $data")
            Timber.i("title: $title")
            Timber.i("body: $body")
            Timber.i("senderId: $senderId")
        }
    }
}