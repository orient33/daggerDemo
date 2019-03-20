package com.example.daggerdemo

import android.content.Context
import android.util.Log
import android.widget.Toast

fun log(msg: String, tag: String = "df") {
    Log.d(tag, msg)
}

fun <E> log(list: List<E>) {
    log(list2String(list))
}

fun <E> list2String(list: List<E>): String {
    val sb = StringBuilder()
    for (e in list) {
        sb.append(e).append(",")
    }
    return sb.toString()
}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}