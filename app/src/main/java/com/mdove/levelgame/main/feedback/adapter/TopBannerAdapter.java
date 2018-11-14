package com.mdove.levelgame.main.feedback.adapter;

import android.content.Context;
import android.widget.TextView;


import com.mdove.levelgame.R;
import com.mdove.levelgame.view.marqueeview.base.CommonAdapter;
import com.mdove.levelgame.view.marqueeview.base.ViewHolder;

import java.util.List;

/**
 * Created by MDove on 2018/11/14.
 */

public class TopBannerAdapter extends CommonAdapter<String> {

    public TopBannerAdapter(Context context, List<String> datas) {
        super(context, R.layout.item_feedback_top_banner, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        TextView tv = viewHolder.getView(R.id.tv_content);
        tv.setText(item);
    }

}
