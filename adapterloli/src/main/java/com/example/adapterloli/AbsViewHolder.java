package com.example.adapterloli;

import android.view.View;

public abstract class AbsViewHolder implements View.OnClickListener {

    protected String TAG = this.getClass().getSimpleName();

    @Override
    public void onClick(View v) {
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V findView(View rootView, int id) {
        if (null == rootView) {
            return null;
        }
        return (V) rootView.findViewById(id);
    }

    public <V extends View> V findViewSetOnClick(View rootView, int id) {
        V view = findView(rootView, id);
        if (null != view) {
            view.setOnClickListener(this);
        }
        return (V) view;
    }

    public <V extends View> V findViewSetOnClick(View rootView, int id, View.OnClickListener onClickListener) {
        V view = findView(rootView, id);
        if (null != view) {
            view.setOnClickListener(onClickListener);
        }
        return (V) view;
    }
}
