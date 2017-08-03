package com.example.adapterloli.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.adapterloli.utils.function.Constants;

/**
 * @version :
 * @FileDescription:
 * @Author:jing
 * @Since:2016/8/17
 * @ChangeList:
 */
public class ViewHolderHelper {

    private SparseArray<View> mViews;
    private View mConvertView;
    private int height;
    private SparseArray<Object> mObjectSparseArray;


    public ViewHolderHelper(View itemView) {
        mConvertView = itemView;
        mViews = new SparseArray<>();
        mObjectSparseArray = new SparseArray<>();
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public void setExtraTag(int key, Object extraTag) {
        if (getExtraTag(key) == null) {
            mObjectSparseArray.put(key, extraTag);
        } else {
            if (getExtraTag(key).equals(extraTag)) {
                return;
            }
            mObjectSparseArray.put(key, extraTag);
        }
    }

    public Object getExtraTag(int key) {
        return mObjectSparseArray.get(key, null);
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolderHelper setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(Constants.EMPTY);
        if (!StringUtils.isEmpty(text)) {
            tv.setText(text);
        }
        return this;
    }

    public ViewHolderHelper setEditText(int viewId, String text) {
        EditText tv = getView(viewId);
        tv.setText(Constants.EMPTY);
        if (!StringUtils.isBlank(text)) {
            tv.setText(text);
        }
        return this;
    }

    public ViewHolderHelper setText(@IdRes int viewId, String s, boolean isFromHtml) {
        TextView textView = getView(viewId);
        if (null == textView) {
            return this;
        }
        if (s != null) {
            textView.setText(isFromHtml ? Html.fromHtml(s) : s);
        } else {
            textView.setText(Constants.EMPTY);
        }
        return this;
    }

    /**
     * text空的时候让textview不显示
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolderHelper setTextIsVisiable(int viewId, String text) {
        TextView tv = getView(viewId);

        if (StringUtils.isBlank(text)) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
        return this;
    }

    public ViewHolderHelper setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolderHelper setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolderHelper setImageLevel(int viewId, int level) {
        ImageView imageView = getView(viewId);
        imageView.setImageLevel(level);
        return this;
    }

    public ViewHolderHelper setSelected(int viewId, boolean isSelecte) {
        View view = getView(viewId);
        view.setSelected(isSelecte);
        return this;
    }

    public ViewHolderHelper displayLocalImg(@IdRes int viewId, @DrawableRes int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolderHelper setLayoutParams(@IdRes int viewId, int width, int height) {
        View view = getView(viewId);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
        return this;
    }

    public ViewHolderHelper setPadding(@IdRes int viewId, int left, int top, int right, int bottom) {
        View view = getView(viewId);
        view.setPadding(left, top, right, bottom);
        return this;
    }

    public ViewHolderHelper setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    @SuppressLint("NewApi")
    public ViewHolderHelper setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public ViewHolderHelper setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewHolderHelper setViewVisibleOrInvisible(int viewId, boolean show) {
        View view = getView(viewId);
        view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        return this;
    }


    public ViewHolderHelper linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public ViewHolderHelper setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ViewHolderHelper setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public ViewHolderHelper setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ViewHolderHelper setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public ViewHolderHelper setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public ViewHolderHelper setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        view.setNumStars(max);
        return this;
    }

    public ViewHolderHelper setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolderHelper setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewHolderHelper setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public ViewHolderHelper setOnCheckChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
        CheckBox view = getView(viewId);
        view.setOnCheckedChangeListener(listener);
        return this;
    }

    public ViewHolderHelper toggleCheckBox(int viewId) {
        Checkable view = getView(viewId);
        view.toggle();
        return this;
    }


    public ViewHolderHelper setRootBackgroundRes(@DrawableRes int resId) {
        mConvertView.setBackgroundResource(resId);
        return this;
    }

    /**
     * 关于事件的
     */
    public ViewHolderHelper setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolderHelper setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public ViewHolderHelper setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public ViewHolderHelper setRootItemRecycleLayoutParams(int width, int height) {
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(width, height);
        mConvertView.setLayoutParams(params);
        return this;
    }

    public void setCheckBoxText(@IdRes int id, CharSequence text) {
        CheckBox view = getView(id);
        view.setText(StringUtils.isEmpty(text) ? Constants.EMPTY : text);
    }

    public void setCheckBoxText(@IdRes int id, String text) {
        CheckBox view = getView(id);
        view.setText(StringUtils.isBlank(text) ? Constants.EMPTY : text);
    }

    public static void setTextViewEmptyAndGone(TextView textView) {
        if (null != textView) {
            textView.setText(Constants.EMPTY);
            textView.setVisibility(View.GONE);
        }
    }

    public String getTextViewText(@IdRes int viewId) {
        TextView textView = getView(viewId);
        return textView.getText().toString().trim();
    }
}
