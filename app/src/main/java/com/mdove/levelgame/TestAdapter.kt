package com.mdove.levelgame

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function

/**
 * Created by MDove on 2018/11/15.
 */
class TestAdapter(var data: List<String>,var context:Context) : RecyclerView.Adapter<TestAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(TextView(context))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.textView.text = "Haha"
    }

    class ViewHolder(var textView: TextView) : RecyclerView.ViewHolder(textView)
}