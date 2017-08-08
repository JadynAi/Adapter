package com.example.adapterloli;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapterloli.utils.ClassUtils;
import com.example.adapterloli.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;



public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements IAdapter {

    private static final String TAG = "RecyclerAdapter";
    private Activity mActivity;
    private ItemCreator mItemCreator;
    private List<Object> mDataList = new ArrayList<>();

    protected RecyclerAdapter(Activity activity, ItemCreator itemCreator) {
        mActivity = activity;
        mItemCreator = itemCreator;
    }

    public List getDataList() {
        return mDataList;
    }

    public void setData(List dataList) {
        if (DataUtils.setAll(mDataList, dataList)) {
            this.notifyDataSetChanged();
        }
    }

    public void setData(List dataList, @NonNull DiffCallback diffCallback) {
        if (diffCallback == null) {
            setData(dataList);
            return;
        }
        diffCallback.setData(new ArrayList<>(mDataList), dataList);
        if (!DataUtils.setAll(mDataList, dataList)) {
            return;
        }
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        diffResult.dispatchUpdatesTo(this);
    }

    public void notifyItemChangedSafe(List newData, int position) {
        if (!DataUtils.setAll(mDataList, newData)) {
            return;
        }
        if (position < 0 || position >= mDataList.size()) {
            notifyDataSetChanged();
            return;
        }
        notifyItemChanged(position);
    }

    public Object getItem(int position) {
        return DataUtils.getItem(mDataList, position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AbsItemView itemView = mItemCreator.createItemView(viewType);
        ItemBusiness itemBusiness = mItemCreator.createItemBusiness(itemView, this);
        ViewHolder recyclerViewHolder = itemView.createRecyclerViewHolder(mActivity, parent, itemBusiness);
        mItemCreator.initItemClick(itemBusiness, recyclerViewHolder.getItemView());
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        LogUtils.d(TAG, "position:" + position + "  isRecyclable:" + holder.isRecyclable());
        holder.getItemBusiness().setItemData(position, getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (mItemCreator.isSingleTypeItem()) {
            return super.getItemViewType(position);
        }
        return mItemCreator.getItemViewType(getItem(position).getClass());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

   
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View mItemView;
        private ItemBusiness<Object> mItemBusiness;

        public ViewHolder(View itemView, ItemBusiness<Object> itemBusiness) {
            super(itemView);
            mItemView = itemView;
            mItemBusiness = itemBusiness;
        }

        ItemBusiness<Object> getItemBusiness() {
            return mItemBusiness;
        }

        View getItemView() {
            return mItemView;
        }
    }

    
    public static class DiffCallback extends DiffUtil.Callback {

        private List mOldData;
        private List mNewData;

        public void setData(@NonNull List oldData, @NonNull List newData) {
            mOldData = oldData == null ? new ArrayList() : oldData;
            mNewData = newData == null ? new ArrayList() : newData;
        }

        @Override
        public int getOldListSize() {
            return mOldData.size();
        }

        @Override
        public int getNewListSize() {
            return mNewData.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return mOldData.get(oldItemPosition) == mNewData.get(newItemPosition);
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return mOldData.get(oldItemPosition).equals(mNewData.get(newItemPosition));
        }
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
            dataList.clear();
            mDataList = dataList;
            return this;
        }

        public RecyclerAdapter build() {
            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(mActivity, buildItemCreator());
            recyclerAdapter.mDataList = mDataList;
            return recyclerAdapter;
        }
    }
}
