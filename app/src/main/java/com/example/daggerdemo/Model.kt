package com.example.daggerdemo

import android.content.Context
import android.provider.BaseColumns
import android.provider.MediaStore
import androidx.annotation.WorkerThread
import android.support.v4.media.MediaDescriptionCompat
import java.util.*
import java.util.Collections.emptyList

class Model(private val context: Context) {

    @WorkerThread
    fun loadMediaItem(): List<MediaDescriptionCompat> {
        val cursor = MediaStore.Video.query(context.contentResolver,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null)
        if (cursor != null) {
            val result = ArrayList<MediaDescriptionCompat>()
            val idIndex = cursor.getColumnIndex(BaseColumns._ID)
            val pathIndex = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA)
            val titleIndex = cursor.getColumnIndex(MediaStore.Video.VideoColumns.TITLE)
            val subTitleIndex = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME)
            if (cursor.moveToFirst()) {
                var path: String
                var title: String
                var subTitle: String
                var id: Long
                do {
                    id = cursor.getLong(idIndex)
                    path = cursor.getString(pathIndex)
                    title = cursor.getString(titleIndex)
                    subTitle = cursor.getString(subTitleIndex)
                    val vi = MediaDescriptionCompat.Builder()
                            .setTitle(title)
                            .setSubtitle(subTitle)
                            .setDescription(path)
                            .setMediaId(id.toString())
                            .build()
                    result.add(vi)
                } while (cursor.moveToNext())
                cursor.close()
                return result
            }
        }
        return emptyList<MediaDescriptionCompat>()
    }
}
