package com.mdove.levelgame.main.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.SwitchCompat
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.mdove.levelgame.R
import com.mdove.levelgame.base.BaseActivity
import com.mdove.levelgame.base.RxTransformerHelper
import com.mdove.levelgame.base.sliding.AbsSlideCloseActivity
import com.mdove.levelgame.config.AppConfig
import com.mdove.levelgame.greendao.utils.InitDataFileUtils
import com.mdove.levelgame.main.feedback.FeedBackActivity
import com.mdove.levelgame.utils.ToastHelper
import com.mdove.levelgame.view.MyDialog
import com.ss.android.network.threadpool.FastMain
import com.ss.android.network.threadpool.MDoveBackgroundPool
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by MDove on 2018/11/13.
 */
class SettingActivity : BaseActivity() {

    lateinit var btnHelp: TextView
    lateinit var btnReStart: TextView
    lateinit var btnUpdateMes: TextView
    lateinit var btnUpdateDB: TextView
    lateinit var btnFeedBack: TextView
    lateinit var switchBigMonster: SwitchCompat
    lateinit var layoutBigMonster: RelativeLayout

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
        btnUpdateDB = findViewById(R.id.btn_update_db)
        btnReStart = findViewById(R.id.btn_re_start)
        btnUpdateMes = findViewById(R.id.btn_update_mes)
        btnFeedBack = findViewById(R.id.btn_feed_back)
        switchBigMonster = findViewById(R.id.toggle_switch_big_monster)
        layoutBigMonster = findViewById(R.id.layout_big_monster)

        btnReStart.setOnClickListener {
            MyDialog.showMyDialog(context, getString(R.string.string_re_start_dialog_title), getString(R.string.string_re_start_dialog_content)
                    , "不，点错了", "确定", false) {
                restartGame()
            }
        }
        btnUpdateDB.setOnClickListener {
            CoroutineScope(FastMain).launch {
                showLoadingDialog(getString(R.string.string_update_db_loading))
                withContext(MDoveBackgroundPool) {
                    InitDataFileUtils.updateDB()
                }
                dismissLoadingDialog()
            }
        }
        btnHelp.setOnClickListener { MyDialog.showMyDialog(context, getString(R.string.string_setting_help_title), getString(R.string.string_setting_help_content), true) }
        btnUpdateMes.setOnClickListener { MyDialog.showMyDialog(context, getString(R.string.string_update_mes), getString(R.string.string_update_mes_content), true) }
        btnFeedBack.setOnClickListener {
            FeedBackActivity.start(context)
        }

        switchBigMonster.isChecked = AppConfig.enableBigMonster()
        switchBigMonster.isClickable = false
        layoutBigMonster.setOnClickListener {
            if (switchBigMonster.isChecked) {
                MyDialog.showMyDialog(context, getString(R.string.string_setting_switch_big_monster_title), getString(R.string.string_setting_switch_big_monster_content)
                        , "不，点错了", "确定", false) {
                    switchBigMonster.isChecked = false
                    AppConfig.setSwitchBigMonster(switchBigMonster.isChecked)
                }
            } else {
                MyDialog.showMyDialog(context, getString(R.string.string_setting_switch_cancel_big_monster_title), getString(R.string.string_setting_switch_cancel_big_monster_content)
                        , "不，点错了", "确定", false) {
                    switchBigMonster.isChecked = true
                    AppConfig.setSwitchBigMonster(switchBigMonster.isChecked)
                    restartGame()
                }
            }
        }
    }

    private fun restartGame() {
        showLoadingDialog(getString(R.string.string_re_start_loading))
        Observable.create(ObservableOnSubscribe<Int> { e ->
            AppConfig.setHasLogin(false)
            InitDataFileUtils.initData()
            AppConfig.setHasLogin()
            e.onNext(0)
        }).compose(RxTransformerHelper.schedulerTransf()).subscribe {
            dismissLoadingDialog()
            HomeActivity.start(context)
            ToastHelper.shortToast(getString(R.string.string_place_re_start))
        }
    }
}