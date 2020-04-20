package com.baselibrary.widget.pagegridview;

import android.view.View;
import android.widget.ImageView;

/**
 * 作者：Malone
 * 时间： 2018/9/21.
 * 邮箱：178917169@qq.com
 * 功能描述：
 */
public abstract class TypeModel<T> {

    private T t;

    public TypeModel(T t) {
        this.t = t;
    }

    public abstract static class ItemModel {
    }

    /**
     * 返回item名字
     *
     * @return
     */
    protected abstract String getItemName();

    /**
     * 设置图标
     *
     * @param imageView
     */
    protected abstract void setIcon(ImageView imageView);

    /**
     * 特殊需求，重写该方法，设置item
     *
     * @param itemView
     */
    protected void setItemView(View itemView) {

    }
}



