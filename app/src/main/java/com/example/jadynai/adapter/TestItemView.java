package com.example.jadynai.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adapterloli.AbsItemView;
import com.example.adapterloli.ItemBusiness;

/**
 * @version:
 * @FileDescription:
 * @Author:jing
 * @Since:2017/8/4
 * @ChangeList:
 */

public class TestItemView extends AbsItemView<ItemBusiness, TestBean> {

    private TextView mTestTv;

    @Override
    protected View onCreateView(Activity activity, ViewGroup parent, ItemBusiness itemBusiness) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_layout, null);
        mTestTv = findView(view, R.id.test_tv);
        return view;
    }

    @Override
    public void showItem(TestBean itemData) {
        mTestTv.setText(itemData.s);
    }
}
