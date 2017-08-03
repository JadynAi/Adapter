package com.example.adapterloli;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbsItemView<B extends ItemBusiness, D> extends AbsViewHolder {

    private int mDataClassHash = 0;

    protected RecyclerAdapter.ViewHolder createRecyclerViewHolder(Activity activity, ViewGroup parent, B itemBusiness) {
        View itemView = onCreateView(activity, parent, itemBusiness);
        return new RecyclerAdapter.ViewHolder(itemView, itemBusiness);
    }

    protected abstract View onCreateView(Activity activity, ViewGroup parent, B itemBusiness);

    public abstract void showItem(D itemData);

    public boolean isMatch(Class itemDataClass) {
        return itemDataClass.hashCode() == mDataClassHash;
    }

    void setDataClassHash(int dataClassHash) {
        mDataClassHash = dataClassHash;
    }
}
