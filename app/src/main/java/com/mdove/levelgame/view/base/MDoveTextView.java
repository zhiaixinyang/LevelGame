package com.mdove.levelgame.view.base;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by MDove on 2018/12/25.
 */
public class MDoveTextView extends AppCompatTextView {

    public MDoveTextView(Context context) {
        super(context);
    }

    public MDoveTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MDoveTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (StackOverflowError e) {
            throw new RuntimeException(getPath() + " " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException(getPath() + " " + e.getMessage(), e);
        }
    }

    public String getPath() {
        StringBuilder sb = new StringBuilder();
        View current = this;
        while (current != null) {
            sb.append(current.toString() + "->");
            try {
                current = (View) current.getParent();
            } catch (Exception e) {
                current = null;
            }
        }
        return sb.toString();
    }

}
