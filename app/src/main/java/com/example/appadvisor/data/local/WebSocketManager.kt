package com.example.appadvisor.data.local

import android.annotation.SuppressLint
import android.util.Log
import com.example.appadvisor.data.model.request.ChatMessageRequest
import com.example.appadvisor.data.model.request.EditChatRequest
import com.example.appadvisor.data.model.response.MessageResponse
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import javax.inject.Inject

class WebSocketManager @Inject constructor(
    private val tokenManager: TokenManager
) {
    private lateinit var stompClient: StompClient

    @SuppressLint("CheckResult")
    fun connect(conversationId: Long, onMessageReceived: (MessageResponse) -> Unit) {

        val token = runBlocking { tokenManager.getToken() }

        val url = "ws://10.0.2.2:8080/ws-chat/websocket?token=$token"

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

        stompClient.lifecycle().subscribe {
            when (it.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.d("WebSocket", "Connected")

                    // Subscribe để nhận tin nhắn
                    stompClient.topic("/topic/chat/$conversationId").subscribe { topicMsg ->
                        val message = Gson().fromJson(topicMsg.payload, MessageResponse::class.java)
                        onMessageReceived(message)
                    }
                }
                LifecycleEvent.Type.ERROR -> Log.e("WebSocket", "Error", it.exception)
                else -> {}
            }
        }

        stompClient.connect()
    }

    fun sendMessage(conversationId: Long, messageText: String) {
        val request = ChatMessageRequest(
            conversationId = conversationId,
            message = messageText,
        )

        val json = Gson().toJson(request)
        Log.d("StompSend", "Sending JSON: $json")
        stompClient.send("/app/chat.message", json).subscribe()
    }

    fun editMessage(messageId: Long, messageText: String) {
        val request = EditChatRequest(
            messageId = messageId,
            message = messageText
        )

        val json = Gson().toJson(request)
        Log.d("StompSend", "Sending edit JSON: $json")
        stompClient.send("/app/chat.message.edit",json).subscribe()
    }

    fun disconnect() {
        stompClient.disconnect()
    }
}


