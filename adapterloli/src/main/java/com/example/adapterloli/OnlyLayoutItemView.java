package com.example.adapterloli;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class OnlyLayoutItemView extends AbsItemView<ItemBusiness, OnlyLayoutItemData> {

    private FrameLayout rootLayout;
    private Activity mActivity;
    private int mLayoutId;

    @Override
    protected View onCreateView(Activity activity, ViewGroup parent, ItemBusiness itemBusiness) {
        rootLayout = new FrameLayout(activity);
        mActivity = activity;
        return rootLayout;
    }

    @Override
    public void showItem(OnlyLayoutItemData itemData) {
        int layoutId = itemData.getLayoutId();
        if (layoutId == mLayoutId) {
            return;
        }
        mLayoutId = layoutId;
        rootLayout.removeAllViews();
        View view = LayoutInflater.from(mActivity).inflate(layoutId, rootLayout);
        rootLayout.addView(view);
    }

    public int getLayoutId() {
        return mLayoutId;
    }
}
