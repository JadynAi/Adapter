package com.example.adapterloli;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapterloli.utils.ViewHolderHelper;

public abstract class AbsItemView<B extends ItemBusiness, D> extends AbsViewHolder {

    private int mDataClassHash = 0;
    private ViewHolderHelper mHolderHelper;

    protected RecyclerAdapter.ViewHolder createRecyclerViewHolder(Activity activity, ViewGroup parent, B itemBusiness) {
        View itemView = onCreateView(activity, parent, itemBusiness);
        return new RecyclerAdapter.ViewHolder(itemView, itemBusiness);
    }

    protected final View onCreateView(Activity activity, ViewGroup parent, B itemBusiness) {
        View view = LayoutInflater.from(activity).inflate(getLayoutId(), null);
        mHolderHelper = new ViewHolderHelper(view);
        initView(activity, parent, itemBusiness, mHolderHelper);
        return view;
    }

    protected abstract void initView(Activity activity, ViewGroup parent, B itemBusiness, ViewHolderHelper holderHelper);

    @LayoutRes
    protected abstract int getLayoutId();

    public abstract void showItem(ViewHolderHelper holderHelper, D itemData);

    public ViewHolderHelper getHolderHelper() {
        return mHolderHelper;
    }

    public boolean isMatch(Class itemDataClass) {
        return itemDataClass.hashCode() == mDataClassHash;
    }

    void setDataClassHash(int dataClassHash) {
        mDataClassHash = dataClassHash;
    }
}
