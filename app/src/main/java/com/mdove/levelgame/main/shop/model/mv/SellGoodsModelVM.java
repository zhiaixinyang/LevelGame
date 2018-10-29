package com.mdove.levelgame.main.shop.model.mv;

import android.databinding.ObservableField;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;

/**
 * @author MDove on 2018/10/29
 */
public class SellGoodsModelVM {
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();
    public ObservableField<String> type = new ObservableField<>();
    public ObservableField<String> price = new ObservableField<>();
    public ObservableField<Long> realPrice = new ObservableField<>();

    public SellGoodsModelVM(long price, String name, String tips, String type) {
        this.name.set(name);
        this.tips.set(tips);
        this.type.set(type);
        this.price.set(String.format(App.getAppContext().getString(R.string.shop_armor_msg_price), price));
        realPrice.set(price);
    }
}
