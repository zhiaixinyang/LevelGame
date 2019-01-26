package com.mdove.levelgame.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.kotlin.ActivityLauncher;
import com.mdove.levelgame.base.kotlin.JobHandler;
import com.mdove.levelgame.base.sliding.BuzzAbsSlideCloseActivity;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.home.HomeActivity;
import com.mdove.levelgame.view.MyDialog;
import com.mdove.levelgame.view.MyProgressDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;

/**
 * @author MDove on 2018/2/14.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView, ActivityLauncher, LifecycleOwner
        , JobHandler {
    private MyProgressDialog progressDialog;
    private Toolbar mToolbar;
    private TextView mLayoutEmpty;
    private FrameLayout mContentContainer;
    private TextView btnAttr, btnGoHome;
    private List<IBackHandler> mBackHandlers = new ArrayList<>();
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    private ResultHandleHelper mResultHandlerHelper = new ResultHandleHelper(this);

    @Override
    public Job getJob() {
        return JobKt.Job(null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hideStatusBar();
        if (!isNeedCustomLayout()) {
            super.setContentView(R.layout.activity_base);

            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            btnAttr = findViewById(R.id.btn_attributes);
            btnGoHome = findViewById(R.id.btn_go_home);
            mContentContainer = (FrameLayout) findViewById(R.id.content_frame);
            mLayoutEmpty = findViewById(R.id.layout_empty);
            mLayoutEmpty.setVisibility(View.GONE);

            setSupportActionBar(mToolbar);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                btnAttr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyDialog.showMyDialog(BaseActivity.this, "个人属性", HeroAttributesManager.getInstance().formatAttributesString(), true);
                    }
                });
                btnGoHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HomeActivity.start(BaseActivity.this);
                    }
                });
            }
        }
    }

    private Drawable resizeImage(int resId, int w, int h) {
        // load the origial Bitmap
        Bitmap BitmapOrg = BitmapFactory.decodeResource(getResources(), resId);
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;
        // calculate the scale
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(resizedBitmap);
    }

    protected void setDataIsEmpty(boolean isEmpty) {
        if (isEmpty) {
            mLayoutEmpty.setVisibility(View.VISIBLE);
        } else {
            mLayoutEmpty.setVisibility(View.GONE);
        }
    }

    protected void customEmptyText(String customText) {
        mLayoutEmpty.setText(customText);
    }

    @Override
    public final void setContentView(@LayoutRes int layoutResID) {
        if (!isNeedCustomLayout()) {
            if (mContentContainer != null) {
                mContentContainer.removeAllViews();
                getLayoutInflater().inflate(layoutResID, mContentContainer);
            }
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public final void setContentView(View view) {
        if (!isNeedCustomLayout()) {
            if (mContentContainer != null) {
                mContentContainer.removeAllViews();
                mContentContainer.addView(view);
            }
        } else {
            super.setContentView(view);
        }
    }

    @Override
    public final void setContentView(View view, ViewGroup.LayoutParams params) {
        if (!isNeedCustomLayout()) {
            if (mContentContainer != null) {
                mContentContainer.removeAllViews();
                mContentContainer.addView(view, params);
            }
        } else {
            super.setContentView(view, params);
        }
    }

    @Override
    public final void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
        if (!isNeedCustomLayout()) {
            if (mContentContainer != null) {
                mContentContainer.addView(view, params);
            }
        } else {
            super.addContentView(view, params);
        }
    }

    protected final void removeContentView(View view) {
        if (!isNeedCustomLayout()) {
            mContentContainer.removeView(view);
        }
    }

    @Override
    public final void onBackPressed() {
        onBackPressed(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected final void setActionBarUpIndicator(@DrawableRes int resId) {
        mToolbar.setNavigationIcon(resId);
    }

    protected final void setActionBarUpIndicator(Drawable icon) {
        mToolbar.setNavigationIcon(icon);
    }

    protected final void setToolBarColor(@ColorInt int backgroundColor) {
        mToolbar.setBackgroundColor(backgroundColor);
    }


    @Override
    public final void setTitleColor(@ColorInt int textColor) {
        super.setTitleColor(textColor);
        mToolbar.setTitleTextColor(textColor);
    }

    @Override
    public final void setTitle(int titleId) {
        super.setTitle(titleId);
        mToolbar.setTitle(titleId);
    }

    @Override
    public final void setTitle(CharSequence title) {
        super.setTitle(title);
        mToolbar.setTitle(title);
    }

    protected void onBackPressed(boolean fromKey) {
        if (handleBack(fromKey)) {
            return;
        }
        if (isTaskRoot()) {
            Intent backIntent = createBackIntent();
            if (backIntent != null) {
                startActivity(backIntent);
                finish();
                return;
            }
        }
        super.onBackPressed();
    }

    protected boolean handleBack(boolean fromKey) {
        if (mBackHandlers.isEmpty()) {
            return false;
        }
        ListIterator<IBackHandler> iterator = mBackHandlers.listIterator(mBackHandlers.size());
        while (iterator.hasPrevious()) {
            if (iterator.previous().handleBackPressed(fromKey)) {
                return true;
            }
        }
        return false;
    }

    protected final void callSuperOnBackPressed() {
        super.onBackPressed();
    }

    protected Intent createBackIntent() {
        return null;
    }

    protected abstract boolean isNeedCustomLayout();

    protected boolean isNeedBaseMenu() {
        return false;
    }

    protected void onClickMenuSend() {

    }

    @Override
    public void addResultHandler(int requestCode, IResultHandler handler) {
        mResultHandlerHelper.addHandler(requestCode, handler);
    }

    @Override
    public boolean removeResultHandler(@NotNull IResultHandler handler) {
        return mResultHandlerHelper.removeHandler(handler);
    }

    @Override
    public IResultHandler removeResultHandler(int requestCode) {
        return mResultHandlerHelper.removeHandler(requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mResultHandlerHelper.handleActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void startActForResult(@NotNull Intent intent, int requestCode, @Nullable Bundle options) {
        ActivityCompat.startActivityForResult(this, intent, requestCode, options);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);
    }

    public interface IBackHandler {
        boolean handleBackPressed(boolean fromKey);
    }

    public final void addBackHandler(IBackHandler backHandler) {
        if (mBackHandlers.contains(backHandler)) {
            mBackHandlers.remove(backHandler);
        }
        mBackHandlers.add(backHandler);
    }

    public final void removeBackHandler(IBackHandler backHandler) {
        mBackHandlers.remove(backHandler);
    }

    @Override
    public void showLoadingDialog(final String msg) {
        if (isFinishing()) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog == null) {
                    progressDialog = new MyProgressDialog(BaseActivity.this);
                    progressDialog.setCancelable(false);
                }
                progressDialog.setMessage(msg);
                progressDialog.show();
            }
        });
    }

    @Override
    public void dismissLoadingDialog() {
        if (isFinishing()) {
            return;
        }
        runOnUiThread(() -> {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }


    @Override
    public Context getContext() {
        return this;
    }
}
