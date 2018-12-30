package com.mdove.levelgame.main.home.city.presenter;

import com.mdove.levelgame.greendao.CityDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.City;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.home.city.model.CityReps;
import com.mdove.levelgame.main.home.city.model.CityVM;
import com.mdove.levelgame.main.home.city.presenter.contract.CityContract;

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
        List<City> data = mCityDao.queryBuilder()
                .orderAsc(CityDao.Properties.Position).list();
        for (City city : data) {
            mData.add(new CityVM(city));
        }
        mView.showData(mData);
    }

    @Override
    public void onClick(CityVM cityVM) {
        if (cityVM.isCurPlace().get()) {
            mView.enterPlaceIsCur(cityVM.getName().get());
            return;
        }
        CityReps cityReps = new CityReps();
        cityReps.setPlaceId(cityVM.getId().get());
        cityReps.setMonsterPlace(cityVM.isMonsterPlace().get());
        cityReps.setPlaceTitle(cityVM.getName().get());
        mView.onClickCity(cityReps);
    }
}