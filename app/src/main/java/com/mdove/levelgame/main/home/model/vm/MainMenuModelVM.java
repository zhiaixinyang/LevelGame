package com.mdove.levelgame.main.home.model.vm;

import android.databinding.ObservableField;

import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.entity.MainMenu;
import com.mdove.levelgame.greendao.utils.SrcIconMap;

/**
 * @author MDove on 2018/10/31
 */
public class MainMenuModelVM extends BaseMainMenuVM{
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<Long> clickId = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> npcGuide = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();
    public ObservableField<String> btnText = new ObservableField<>();
    public ObservableField<Integer> src = new ObservableField<>();
    public ObservableField<Integer> topSrc = new ObservableField<>();

    public MainMenuModelVM(MainMenu mainMenu, String npcGudie) {
        id.set(mainMenu.id);
        clickId.set(mainMenu.clickId);
        name.set(mainMenu.name);
        tips.set(mainMenu.tips);
        btnText.set(mainMenu.btnText);
        src.set(SrcIconMap.getInstance().getSrc(mainMenu.getType()));
        topSrc.set(getItemTopSrc(mainMenu.getType()));
        this.npcGuide.set(npcGudie);
    }

    public int getItemTopSrc(String type) {
        switch (type) {
            case "main1": {
                return R.mipmap.ic_home_item_1;
            }
            case "main2": {
                return R.mipmap.ic_home_item_2;
            }
            case "main3": {
                return R.mipmap.ic_home_item_3;
            }
            case "main4": {
                return R.mipmap.ic_home_item_4;
            }
            case "main5": {
                return R.mipmap.ic_home_item_5;
            }
            case "main6": {
                return R.mipmap.ic_home_item_4;
            }
            case "main7": {
                return R.mipmap.ic_home_item_3;
            }
            default: {
                return R.mipmap.ic_home_item_3;
            }
        }
    }
}
