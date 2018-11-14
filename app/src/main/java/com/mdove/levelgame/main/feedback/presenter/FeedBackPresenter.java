package com.mdove.levelgame.main.feedback.presenter;

import android.text.TextUtils;


import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.base.RxTransformerHelper;
import com.mdove.levelgame.main.feedback.presenter.contract.FeedBackContract;
import com.mdove.levelgame.utils.NetUtil;
import com.mdove.levelgame.utils.ToastHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
/**
 * Created by MDove on 2018/11/14.
 */
public class FeedBackPresenter implements FeedBackContract.IFeedBackPresenter {
    private FeedBackContract.IFeedBackView mView;

    public FeedBackPresenter() {
    }

    @Override
    public void subscribe(FeedBackContract.IFeedBackView view) {
        mView = view;
    }

    @Override
    public void unSubscribe() {

    }


    @Override
    public void sendFeedBack(String content) {
        if (NetUtil.isNetworkAvailable(mView.getContext())) {
            App.getApiServer().feedBack(content)
                    .compose(RxTransformerHelper.<ResponseBody>schedulerTransf()).
                    subscribe(new Consumer<ResponseBody>() {
                        @Override
                        public void accept(ResponseBody responseBody) throws Exception {
                            String result = "";
                            try {
                                result = responseBody.string().trim();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (TextUtils.equals(result, "{result=1}")) {
                                mView.sendFeedBackReturn(1);
                            } else {
                                mView.sendFeedBackReturn(0);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            ToastHelper.shortToast(R.string.string_feedback_err);
                        }
                    });
        } else {
            ToastHelper.shortToast(R.string.string_no_network);
        }
    }

    @Override
    public void initTopBanner() {
        List<String> arr = new ArrayList<>();
        arr.add("我是这个“游戏”的开发者：MDove");
        arr.add("姑且把它称之为游戏吧");
        arr.add("不知道有没有程序员同行");
        arr.add("这个游戏是纯Java写的");
        arr.add("如果有同行，可以关注一下我的公众号：");
        arr.add("IT面试填坑小分队，一起交流");
        arr.add("！！欢迎反馈：任何问题、吐槽、建议~！！");
        arr.add("我会不断改进哒~");
        mView.initTopBanner(arr);
    }
}