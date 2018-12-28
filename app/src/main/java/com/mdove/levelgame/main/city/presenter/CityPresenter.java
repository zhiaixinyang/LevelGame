package com.mdove.levelgame.main.city.presenter;

import com.mdove.levelgame.greendao.CityDao;
import com.mdove.levelgame.greendao.FbPlaceDao;
import com.mdove.levelgame.greendao.entity.City;
import com.mdove.levelgame.greendao.entity.FbPlace;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.city.model.CityVM;
import com.mdove.levelgame.main.city.presenter.contract.CityContract;
import com.mdove.levelgame.main.fb.presenter.contract.FbPlaceContract;
import com.mdove.levelgame.main.fb.viewmodel.FbPlaceVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2018/12/26.
 */
public class CityPresenter implements CityContract.ICityPresenter {
    private CityContract.ICityView mView;
    private CityDao mCityDao;
    private List<CityVM> mData;

    public CityPresenter() {
        mCityDao = DatabaseManager.getInstance().getCityDao();
    }

    @Override
    public void subscribe(CityContract.ICityView view) {
        mView = view;
    }

    @Override
    public void unSubscribe() {

    }


    @Override
    public void initData() {
        mData = new ArrayList<>();
        List<City> data = mCityDao.queryBuilder().list();
        for (City city : data) {
            mData.add(new CityVM(city));
        }
        mView.showData(mData);
    }

    @Override
    public void onClick(CityVM cityVM) {
        mView.onClickCity();
    }
}