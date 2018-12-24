package com.mdove.levelgame.base.listener;

import android.view.View;

/**
 * Created by MDove on 18/12/24.
 */
public abstract class DebounceOnClickListener implements View.OnClickListener {
    static boolean enabled = true;
    private long delay = 200L;
    private static final Runnable ENABLE_AGAIN = new Runnable() {
        public void run() {
            DebounceOnClickListener.enabled = true;
        }
    };

    public DebounceOnClickListener() {
    }

    public DebounceOnClickListener(long delay) {
        this.delay = delay;
    }

    public final void onClick(View v) {
        if(enabled) {
            enabled = false;
            v.postDelayed(ENABLE_AGAIN, delay);
            this.doClick(v);
        }

    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public abstract void doClick(View var1);
}