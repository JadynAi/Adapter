package com.example.adapterloli;

import android.os.SystemClock;

import java.util.List;

public class ItemBusiness<D> {

    private int mPosition;
    private D mItemData;
    private long mDataTimestamp = 0;
    private IAdapter mAdapter;
    private AbsItemView mItemView;
    private boolean mIsSetItemClick;

    public ItemBusiness(AbsItemView itemView) {
        mItemView = itemView;
    }

    public ItemBusiness setSetItemClick(boolean setItemClick) {
        mIsSetItemClick = setItemClick;
        return this;
    }

    public void onItemClick() {
    }

    public boolean onItemLongClick() {
        return false;
    }

    public final long createTimestamp() {
        return SystemClock.elapsedRealtime();
    }

    public final boolean isDataChangedSince(long timestamp) {
        return timestamp < mDataTimestamp;
    }

    public final boolean notifyDataSetChanged(long timestamp) {
        if (isDataChangedSince(timestamp)) {
            notifyDataSetChanged();
            return true;
        }

        onSetItemData(false, false, false);
        return false;
    }

    public final void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    public final void notifyItemDataChanged() {
        setItemData(mPosition, mItemData);
    }

    public final int getCount() {
        return mAdapter.getItemCount();
    }

    public final D getItemData() {
        return mItemData;
    }

    public final int getPosition() {
        return mPosition;
    }

    protected final List<?> getAllData() {
        return mAdapter.getDataList();
    }

    protected void onSetItemData(boolean isFirstSetData, boolean isPosChanged, boolean isDataChanged) {
        mItemView.showItem(mItemView.getHolderHelper(), getItemData());
    }

    boolean isSetItemClick() {
        return mIsSetItemClick;
    }

    void setAdapter(IAdapter adapter) {
        mAdapter = adapter;
    }

    void setItemData(int positon, D itemData) {
        boolean isPosChanged = positon != mPosition;
        boolean isDataChanged = itemData != mItemData;
        boolean isFirstSetData = mDataTimestamp == 0;
        if (isDataChanged || isPosChanged || isFirstSetData) {
            mPosition = positon;
            mItemData = itemData;
            mDataTimestamp = SystemClock.elapsedRealtime();
        }
        onSetItemData(isFirstSetData, isPosChanged, isDataChanged);
    }
    
    public interface Factory {

        ItemBusiness create(AbsItemView itemView);
    }

    public interface Supplier<V extends AbsItemView> {

        ItemBusiness get(V itemView);
    }
}
