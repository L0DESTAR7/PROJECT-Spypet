package com.example.spypetest


import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException
import org.json.JSONObject

object SocketHandler {

    lateinit var mSocket: Socket

    fun setSocket() {
        try {
            val options = IO.Options();
            val mutableList: MutableList<String> = mutableListOf();
            mutableList.add("ANDROID");
            options.extraHeaders = mutableMapOf(
                "spypet-device-type" to mutableList
            );
            options.auth = mutableMapOf(
                "token" to "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjQxNjhmOGJhLWNmMTQtNDViOS05OTU3LWRmYzUxYjNlNDIxOSIsImVtYWlsIjoidGFvdWZpa2toYXJpajEzQGdtYWlsLmNvbSIsIm5hbWUiOiJUYXdmdSIsInBhc3N3b3JkIjoiJDJiJDEwJFQ4UVFVTlBEQ1NQVVNpdHpsRlBUak9RckwxMW9kcGV4am9JWjNSelJZVHBGWTAuVlBKeFB5IiwiaWF0IjoxNjg0ODQxNjk0fQ.YNRT1P1BXJVwp9KaGiIft5B-etOEO42fFfXuDwgNtW0"
            );
            mSocket = IO.socket("http://10.0.2.2:8080",options);
        } catch (e: URISyntaxException) {
        }
    }


    fun getSocket(): Socket {
        return mSocket
    }


    fun establishConnection() {
        mSocket.connect()
    }


    fun closeConnection() {
        mSocket.disconnect()
    }
}