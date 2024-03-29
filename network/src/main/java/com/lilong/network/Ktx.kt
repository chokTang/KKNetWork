package com.lilong.network

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * 作者　: tyk
 * 时间　: 2019/12/14
 * 描述　:
 */

val appContext: Application by lazy { Ktx.app }

class Ktx : ContentProvider() {

    companion object {
        lateinit var app: Application
        var watchActivityLife = true
        var watchAppLife = true
    }

    override fun onCreate(): Boolean {
        val application = context!!.applicationContext as Application
        install(application)
        return true
    }

    private fun install(application: Application) {
        app = application

    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = null


    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0

    override fun getType(uri: Uri): String? = null
}