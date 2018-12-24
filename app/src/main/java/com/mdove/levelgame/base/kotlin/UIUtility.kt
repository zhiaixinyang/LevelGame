@file:JvmName("UIUtility")
package com.mdove.levelgame.base.kotlin

import android.content.res.Resources
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.mdove.levelgame.base.listener.DebounceOnClickListener
import java.io.File

/**
 * Created by MDove on 18/12/24.
 */
fun ImageView.setImageFile(file: File): Boolean {
    return try {
        setImageURI(Uri.fromFile(file))
        true
    } catch (ignore: Throwable) {
        false
    }
}

fun View.setDebounceOnClickListener(action: (View?) -> Unit) {
    this.setOnClickListener(object : DebounceOnClickListener() {
        override fun doClick(var1: View?) {
            action(var1)
        }
    })
}

fun View.layoutIdTrace(resource: Resources): String {
    var current: View? = this
    val result = StringBuilder()
    while(current != null) {
        try {
            result.append(resource.getResourceName(current.id))
        } catch (e: Exception) {
            break
        }
        result.append(" <- ")
        current = current.parent as? View?
    }
    return result.toString()
}