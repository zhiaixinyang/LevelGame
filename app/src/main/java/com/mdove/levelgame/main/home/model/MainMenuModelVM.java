package com.mdove.levelgame.main.home.model;

import android.databinding.ObservableField;

import com.mdove.levelgame.greendao.entity.MainMenu;
import com.mdove.levelgame.greendao.utils.SrcIconMap;

/**
 * @author MDove on 2018/10/31
 */
public class MainMenuModelVM {
    public ObservableField<Long> id = new ObservableField<>();
    public ObservableField<Long> clickId = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();
    public ObservableField<String> btnText = new ObservableField<>();
    public ObservableField<Integer> src = new ObservableField<>();

    public MainMenuModelVM(MainMenu mainMenu) {
        id.set(mainMenu.id);
        clickId.set(mainMenu.clickId);
        name.set(mainMenu.name);
        tips.set(mainMenu.tips);
        btnText.set(mainMenu.btnText);
        src.set(SrcIconMap.getInstance().getSrc(mainMenu.getType()));
    }
}
