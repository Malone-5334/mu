package com.baselibrary.widget.recycleview;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2016/6/22 21:09
 * <p>
 * RecyclerViewHolder
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> viewSparseArray;
    private Context context;

    public RecyclerViewHolder(Context context, View itemView) {
        super(itemView);
        this.viewSparseArray = new SparseArray<View>();
        this.context = context;

    }

    /**
     * 通过类继承View ,View通过findViewById获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    private <T extends View> T findViewById(int viewId) {
        View view = this.viewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        } else {
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * @return
     */
    public View getView() {
        return itemView;
    }

    /**
     * 通过id获取View
     *
     * @param viewId
     * @return
     */
    public View getView(int viewId) {
        return findViewById(viewId);
    }

    /**
     * 通过id获取TextView
     *
     * @param viewId
     * @return
     */
    public TextView getTextView(int viewId) {
        return (TextView) getView(viewId);
    }

    /**
     * 通过id获取LinearLayout
     *
     * @param viewId
     * @return
     */
    public LinearLayout getLinearLayout(int viewId) {
        return (LinearLayout) getView(viewId);
    }

    /**
     * 通过id获取RelativeLayout
     *
     * @param viewId
     * @return
     */
    public RelativeLayout getRelativeLayout(int viewId) {
        return (RelativeLayout) getView(viewId);
    }

    /**
     * 通过id获取Button
     *
     * @param viewId
     * @return
     */

    public Button getButton(int viewId) {
        return (Button) getView(viewId);
    }

    /**
     * 通过id获取ImageView
     *
     * @param viewId
     * @return
     */

    public ImageView getImageView(int viewId) {
        return (ImageView) getView(viewId);
    }

    /**
     * 通过id获取ImageButton
     *
     * @param viewId
     * @return
     */

    public ImageButton getImageButton(int viewId) {
        return (ImageButton) getView(viewId);
    }

    /**
     * 通过id获取EditText
     *
     * @param viewId
     * @return
     */

    public EditText getEditText(int viewId) {
        return (EditText) getView(viewId);
    }

    public RecyclerViewHolder setText(int viewId, String value) {
        TextView view = findViewById(viewId);
        view.setText(value);
        return this;
    }

}
