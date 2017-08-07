package com.example.adapterloli;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.adapterloli.utils.ViewHolderHelper;

public class OnlyLayoutItemView extends AbsItemView<ItemBusiness, OnlyLayoutItemData> {

    private FrameLayout rootLayout;
    private Activity mActivity;
    private int mLayoutId;
    
    @Override
    public void showItem(ViewHolderHelper holderHelper, OnlyLayoutItemData itemData) {
        int layoutId = itemData.getLayoutId();
        if (layoutId == mLayoutId) {
            return;
        }
        mLayoutId = layoutId;
        rootLayout.removeAllViews();
        View view = LayoutInflater.from(mActivity).inflate(layoutId, rootLayout);
        rootLayout.addView(view);
    }

    @Override
    protected void initView(Activity activity, ViewGroup parent, ItemBusiness itemBusiness, ViewHolderHelper holderHelper) {
        rootLayout = new FrameLayout(activity);
        mActivity = activity;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }
}
