package com.mdove.levelgame.main.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.generated.callback.OnClickListener
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import com.mdove.levelgame.R
import com.mdove.levelgame.base.BaseActivity
import com.mdove.levelgame.base.RxTransformerHelper
import com.mdove.levelgame.config.AppConfig
import com.mdove.levelgame.greendao.utils.InitDataFileUtils
import com.mdove.levelgame.main.feedback.FeedBackActivity
import com.mdove.levelgame.main.home.presenter.SettingContract
import com.mdove.levelgame.utils.ToastHelper
import com.mdove.levelgame.view.MyDialog
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import java.util.function.Consumer

/**
 * Created by MDove on 2018/11/13.
 */
class SettingActivity : BaseActivity() {
    lateinit var btnHelp: TextView
    lateinit var btnReStart: TextView
    lateinit var btnUpdateMes: TextView
    lateinit var btnFeedBack: TextView
    override fun isNeedCustomLayout(): Boolean {
        return false
    }

    companion object {
        fun start(context: Context) {
            val start = Intent(context, SettingActivity::class.java)
            if (context !is Activity) {
                start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(start)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.activity_title_setting)
        setContentView(R.layout.activity_setting)

        btnHelp = findViewById(R.id.btn_help)
        btnReStart = findViewById(R.id.btn_re_start)
        btnUpdateMes = findViewById(R.id.btn_update_mes)
        btnFeedBack = findViewById(R.id.btn_feed_back)

        btnReStart.setOnClickListener {
            MyDialog.showMyDialog(context, getString(R.string.string_re_start_dialog_title), getString(R.string.string_re_start_dialog_content)
                    , "不点错了", "确定", false, {
                showLoadingDialog(getString(R.string.string_re_start_loading))
                Observable.create(ObservableOnSubscribe<Int> { e ->
                    AppConfig.setFirstLogin(false)
                    InitDataFileUtils.initData()
                    AppConfig.setFirstLogin()
                    e.onNext(0)
                }).compose(RxTransformerHelper.schedulerTransf()).subscribe {
                    dismissLoadingDialog()
                    HomeActivity.start(context)
                    ToastHelper.shortToast(getString(R.string.string_place_re_start))
                }
            })
        }
        btnHelp.setOnClickListener { MyDialog.showMyDialog(context, getString(R.string.string_setting_help_title), getString(R.string.string_setting_help_content), true) }
        btnUpdateMes.setOnClickListener { MyDialog.showMyDialog(context, getString(R.string.string_update_mes), getString(R.string.string_update_mes_content), true) }
        btnFeedBack.setOnClickListener {
            FeedBackActivity.start(context)
        }
    }
}