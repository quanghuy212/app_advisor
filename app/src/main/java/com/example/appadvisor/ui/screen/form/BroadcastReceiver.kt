package com.example.appadvisor.ui.screen.form
/*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi

class DownloadReceiver(private val onComplete: () -> Unit) : BroadcastReceiver() {
    private var downloadId: Long = -1

    fun setDownloadId(id: Long) {
        downloadId = id
    }

    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        if (id == downloadId) {
            // Tải xuống hoàn tất
            onComplete() // Gọi callback hoàn thành
            context.unregisterReceiver(this) // Hủy đăng ký receiver
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun register(context: Context) {
        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        // Đăng ký receiver với cờ RECEIVER_EXPORTED
        context.registerReceiver(this, filter, Context.RECEIVER_EXPORTED)
    }
}

*/
