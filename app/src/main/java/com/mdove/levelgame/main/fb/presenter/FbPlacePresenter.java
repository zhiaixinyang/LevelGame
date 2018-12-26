package com.mdove.levelgame.main.fb.presenter;

import com.mdove.levelgame.greendao.FbPlaceDao;
import com.mdove.levelgame.greendao.entity.FbPlace;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.fb.presenter.contract.FbPlaceContract;
import com.mdove.levelgame.main.fb.viewmodel.FbPlaceVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2018/12/26.
 */
public class FbPlacePresenter implements FbPlaceContract.IFbPlacePresenter {
    private FbPlaceContract.IFbPlaceView mView;
    private FbPlaceDao mFbPlaceDao;
    private List<FbPlaceVM> mData;

    public FbPlacePresenter() {
        mFbPlaceDao = DatabaseManager.getInstance().getFbPlaceDao();
    }

    @Override
    public void subscribe(FbPlaceContract.IFbPlaceView view) {
        mView = view;
    }

    @Override
    public void unSubscribe() {

    }


    @Override
    public void initData() {
        mData = new ArrayList<>();
        List<FbPlace> data = mFbPlaceDao.queryBuilder().list();
        for (FbPlace fbPlace : data) {
            mData.add(new FbPlaceVM(fbPlace));
        }
        mView.showData(mData);
    }
}