package com.example.adapterloli;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;

class ItemCreator {

    private SparseArrayCompat<Class<? extends AbsItemView>> mItemViewClasses;
    private ItemBusiness.Factory mItemBusinessFactory;
    private int mViewTypeCount;
    private String mActivitySimpleName;

    private ItemCreator() {
    }

    int getItemViewType(@NonNull Class<?> itemDataClass) {
        int itemViewType = mItemViewClasses.indexOfKey(itemDataClass.hashCode());
        if (itemViewType < 0) {
            throw new RuntimeException("in page:" + mActivitySimpleName
                    + " itemData:" + itemDataClass.getSimpleName() + " not find itemViewClass!");
        }
        return itemViewType;
    }

    boolean isSingleTypeItem() {
        return mViewTypeCount <= 1;
    }

    AbsItemView createItemView(@NonNull Class<?> itemDataClass) {
        return createItemView(getItemViewType(itemDataClass));
    }

    AbsItemView createItemView(int viewType) {
        try {
            AbsItemView itemView = mItemViewClasses.valueAt(viewType).newInstance();
            itemView.setDataClassHash(mItemViewClasses.keyAt(viewType));
            return itemView;
        } catch (Exception e) {
            throw new RuntimeException("in page:" + mActivitySimpleName
                    + " viewType:" + viewType + " createItemView fail!" + e + e.getMessage(), e);
        }
    }

    ItemBusiness createItemBusiness(AbsItemView itemView, IAdapter adapter) {
        ItemBusiness itemBusiness = null;
        if (mItemBusinessFactory != null) {
            itemBusiness = mItemBusinessFactory.create(itemView);
        }
        if (itemBusiness == null) {
            itemBusiness = new ItemBusiness(itemView);
        }
        itemBusiness.setAdapter(adapter);
        return itemBusiness;
    }

    void initItemClick(final ItemBusiness itemBusiness, View itemRootView) {
        if (itemBusiness.isSetItemClick()) {
            itemRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemBusiness.onItemClick();
                }
            });
            
            itemRootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return itemBusiness.onItemLongClick();
                }
            });
        }
    }

    int getViewTypeCount() {
        return mViewTypeCount;
    }

    static class Builder {

        private SparseArrayCompat<Class<? extends AbsItemView>> mItemViewClasses = new SparseArrayCompat<>();
        private ItemBusiness.Factory mItemBusinessFactory;
        private int mViewTypeCount = 0;
        private String mActivitySimpleName;

        Builder(String activitySimpleName) {
            mActivitySimpleName = activitySimpleName;
        }

        Builder putItemClass(int viewType,
                             @NonNull Class<? extends AbsItemView> itemViewClass) {
            mItemViewClasses.put(viewType, itemViewClass);
            mViewTypeCount++;
            return this;
        }

        public Builder setItemBusinessFactory(ItemBusiness.Factory itemBusinessFactory) {
            mItemBusinessFactory = itemBusinessFactory;
            return this;
        }

        ItemCreator buildItemCreator() {
            ItemCreator itemCreator = new ItemCreator();
            itemCreator.mItemViewClasses = mItemViewClasses.clone();
            itemCreator.mItemBusinessFactory = mItemBusinessFactory;
            itemCreator.mViewTypeCount = mViewTypeCount;
            itemCreator.mActivitySimpleName = mActivitySimpleName;
            return itemCreator;
        }
    }
}
