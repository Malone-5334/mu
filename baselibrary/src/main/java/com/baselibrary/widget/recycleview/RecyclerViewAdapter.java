package com.baselibrary.widget.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2016/6/22 19:09
 * <p>
 * RecyclerView 适配器
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Context context;
    private List<T> items;
    private LayoutInflater layoutInflater;
    private int[] layoutRess;
    private int flag;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public RecyclerViewAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = items;
        this.layoutInflater = LayoutInflater.from(context);
        this.flag = -1;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutRess == null) {
            layoutRess = setItemLayout();
        }
        View view = layoutInflater.inflate(layoutRess[viewType], parent, false);
        final RecyclerViewHolder viewHolder = new RecyclerViewHolder(context, view);
        final ViewGroup viewGroup = parent;
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(viewGroup, viewHolder.itemView, viewHolder.getLayoutPosition());
                }
            });
        }

        if (onItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClickListener.onItemLongClick(viewGroup, viewHolder.itemView, viewHolder.getLayoutPosition());
                    return false;
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        bindData(viewHolder, getItemViewType(position), position, flag, items.get(position));
    }

    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return setItemType(items.get(position));
    }

    public int getItemViewCount() {
        return layoutRess.length;
    }

    /**
     * 指定位置添加Item
     *
     * @param position
     * @param item
     */
    public void add(int position, T item) {
        items.add(position, item);
        super.notifyItemInserted(position);
    }

    /**
     * 指定位置删除Item
     *
     * @param position
     */
    public void delete(int position) {
        items.remove(position);
        super.notifyItemRemoved(position);
    }

    /**
     * 获取当前Item类型
     *
     * @param item
     * @return
     */
    public int setItemType(T item) {
        return 0;
    }

    /**
     * 获取当前ItemView控件
     *
     * @return
     */
    public abstract int[] setItemLayout();

    /**
     * 标记位置
     *
     * @param flag
     */
    public void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * 注入单击事件接口
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    /**
     * 注入点长按事件接口
     *
     * @param listener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
    }

    /**
     * 绑定控件数据
     *
     * @param viewHolder
     * @param position
     * @param item
     */
    public abstract void bindData(RecyclerViewHolder viewHolder, int viewType, int position, int flag, T item);

    /**
     * 单击事件接口
     */
    public interface OnItemClickListener {
        void onItemClick(ViewGroup parent, View view, int position);
    }

    /**
     * 长按事件接口
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(ViewGroup parent, View view, int position);
    }


}
