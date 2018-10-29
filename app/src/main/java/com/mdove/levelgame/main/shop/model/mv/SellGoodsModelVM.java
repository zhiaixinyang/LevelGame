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
    public ObservableField<String> btnText = new ObservableField<>();
    public ObservableField<Long> realPrice = new ObservableField<>();
    // 0表示是可购买的，1表示可合成，2表示可升级
    public int status;

    public SellGoodsModelVM(long price, String name, String tips, String type, int status) {
        this.name.set(name);
        this.tips.set(tips);
        this.type.set(type);
        this.status = status;
        if (status == 0) {
            this.price.set(String.format(App.getAppContext().getString(R.string.shop_armor_msg_price), price));
            btnText.set("购买");
        } else if (status == 1) {
            this.price.set("");
            btnText.set("合成");
        } else if (status == 2) {
            this.price.set("");
            btnText.set("升级");
        }
        realPrice.set(price);
    }
}
