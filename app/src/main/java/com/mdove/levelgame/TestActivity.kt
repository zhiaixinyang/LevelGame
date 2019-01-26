package com.mdove.levelgame

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.mdove.levelgame.utils.ToastHelper
import java.util.regex.Pattern
import java.util.Collections.replaceAll


/**
 * Created by zhaojing on 2019/1/24.
 */
class TestActivity : AppCompatActivity() {
    private var tv1: TextView? = null
    private val tv2: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.TransTheme)
        setContentView(R.layout.activity_test)
        tv1 = findViewById(R.id.tv_1)
        tv1!!.setOnClickListener {
            val pattern = Pattern.compile("\\d*")
            val matcher = pattern.matcher("1234567890asdasfasf")
            val errorPattern = Pattern.compile("[0-9]{5}")
            val errorMatcher = errorPattern.matcher("1234567890asdasfasf")
            if (errorMatcher.find()) {
                ToastHelper.shortToast(errorMatcher.group())
            }
            if (matcher.find()) {
            }
        }
    }
}
