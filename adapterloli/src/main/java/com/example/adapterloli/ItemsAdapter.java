package com.example.adapterloli;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.adapterloli.utils.ClassUtils;
import com.example.adapterloli.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends BaseAdapter implements IAdapter {

    private Activity mActivity;
    private ItemCreator mItemCreator;
    private List<Object> mDataList;

    protected ItemsAdapter(Activity activity, ItemCreator itemCreator) {
        mActivity = activity;
        mItemCreator = itemCreator;
    }

    public void setData(List dataList) {
        if (DataUtils.setAll(mDataList, dataList)) {
            this.notifyDataSetChanged();
        }
    }

    public List getDataList() {
        return mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    @Override
    public Object getItem(int position) {
        return DataUtils.getItem(mDataList, position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return mItemCreator.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mItemCreator.isSingleTypeItem()) {
            return super.getItemViewType(position);
        }
        return mItemCreator.getItemViewType(getItem(position).getClass());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemBusiness itemBusiness = null;
        Object itemData = getItem(position);
        if (convertView == null) {
            Class<?> itemDataClass = itemData.getClass();
            AbsItemView itemView = mItemCreator.createItemView(itemDataClass);
            itemBusiness = mItemCreator.createItemBusiness(itemView, this);
            convertView = itemView.onCreateView(mActivity, parent, itemBusiness);
            mItemCreator.initItemClick(itemBusiness, convertView);
            convertView.setTag(itemBusiness);
        } else {
            itemBusiness = (ItemBusiness) convertView.getTag();
        }
        itemBusiness.setItemData(position, itemData);
        return convertView;
    }

    public static Builder create(Activity activity) {
        return new Builder(activity);
    }
    
    public static class Builder extends ItemCreator.Builder {

        private Activity mActivity;
        private List<Object> mDataList = new ArrayList<>();

        public Builder(Activity activity) {
            super(activity.getClass().getSimpleName());
            mActivity = activity;
        }

        public <D> Builder putItemClass(@NonNull Class<? extends D> itemDataClass,
                                        @NonNull Class<? extends AbsItemView<?, D>> itemViewClass) {
            return (Builder) super.putItemClass(itemDataClass.hashCode(), itemViewClass);
        }

        public Builder putItemClass(Class<? extends AbsItemView> itemViewClass) {
            Class itemDataClass = ClassUtils.getGenericsClass(itemViewClass, AbsItemView.class, 1);
            return (Builder) super.putItemClass(itemDataClass.hashCode(), itemViewClass);
        }

        @Override
        public Builder setItemBusinessFactory(ItemBusiness.Factory itemBusinessFactory) {
            return (Builder) super.setItemBusinessFactory(itemBusinessFactory);
        }

        public Builder setDataContainer(List<Object> dataList) {
            if (dataList == null) {
                return this;
            }
            mDataList = dataList;
            return this;
        }

        public ItemsAdapter build() {
            ItemsAdapter itemsAdapter = new ItemsAdapter(mActivity, buildItemCreator());
            itemsAdapter.mDataList = mDataList;
            return itemsAdapter;
        }
    }
}
