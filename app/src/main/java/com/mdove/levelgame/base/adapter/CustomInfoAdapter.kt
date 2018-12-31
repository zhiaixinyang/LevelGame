package com.mdove.levelgame.base.adapter

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mdove.levelgame.R
import com.mdove.levelgame.utils.InflateUtils

/**
 * Created by MDove on 2018/12/31.
 */
class CustomInfoAdapter : BaseListAdapter<String>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(InflateUtils.inflate(parent, R.layout.item_custom_info))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder)?.let {
            it.bind(data[position])
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(info: String) {
            view.findViewById<TextView>(R.id.tv_info).text = Html.fromHtml(info)
        }
    }
}