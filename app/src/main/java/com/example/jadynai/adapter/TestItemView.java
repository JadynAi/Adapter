package com.example.jadynai.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.example.adapterloli.AbsItemView;
import com.example.adapterloli.ItemBusiness;
import com.example.adapterloli.utils.ViewHolderHelper;

/**
 * @version:
 * @FileDescription:
 * @Author:jing
 * @Since:2017/8/4
 * @ChangeList:
 */

public class TestItemView extends AbsItemView<ItemBusiness, TestBean> {

    @Override
    protected void initView(Activity activity, ViewGroup parent, ItemBusiness itemBusiness, ViewHolderHelper holderHelper) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_layout;
    }

    @Override
    public void showItem(ViewHolderHelper holderHelper, TestBean itemData) {
        holderHelper.setText(R.id.test_tv, itemData.s);
    }
}
