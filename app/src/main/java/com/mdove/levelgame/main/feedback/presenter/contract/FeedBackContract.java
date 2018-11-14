package com.mdove.levelgame.main.feedback.presenter.contract;



import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;

import java.util.List;

/**
 * Created by MDove on 2018/9/3.
 */
public interface FeedBackContract {
    interface IFeedBackPresenter extends BasePresenter<IFeedBackView> {
        void sendFeedBack(String content);

        void initTopBanner();
    }

    interface IFeedBackView extends BaseView {
        void sendFeedBackReturn(int status);

        void initTopBanner(List<String> bannerList);
    }
}
