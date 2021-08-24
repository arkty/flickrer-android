package me.arkty.flickrer.utils.helper

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import dagger.hilt.android.qualifiers.ApplicationContext
import me.arkty.flickrer.R
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun enqueue(url: String, filename: String) {
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
            .setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            )
            .setTitle(context.getString(R.string.downloading_title))
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                File.separator + filename
            )

        (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager)
            .enqueue(request)
    }
}