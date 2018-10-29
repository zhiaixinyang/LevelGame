package com.mdove.levelgame.main.monsters.presenter;

import com.mdove.levelgame.greendao.MonstersPlaceDao;
import com.mdove.levelgame.greendao.entity.MonstersPlace;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.monsters.MonstersActivity;

import java.util.List;

/**
 * Created by MDove on 2018/10/21.
 */

public class MonstersPlacePresenter implements MonstersPlaceContract.IMonstersPlacePresenter {
    private MonstersPlaceContract.IMonstersPlaceView view;

    @Override
    public void subscribe(MonstersPlaceContract.IMonstersPlaceView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initData() {
        List<MonstersPlace> places = DatabaseManager.getInstance().getMonstersPlaceDao().queryBuilder()
                .where(MonstersPlaceDao.Properties.IsShow.eq(0)).list();
        if (places==null||places.size()==0){
            return;
        }
        view.showData(places);
    }

    @Override
    public void onItemBtnClick(Long id, String title) {
        MonstersActivity.start(view.getContext(), id, title);
    }
}
