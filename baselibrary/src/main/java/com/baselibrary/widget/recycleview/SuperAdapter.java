package com.baselibrary.widget.recycleview;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

public abstract class SuperAdapter<T> extends BaseAdapter {

    /**
     * 万能适配器
     *
     * @author 大好河山清水秀
     */

    private Context context;
    private List<T> list;
    private LayoutInflater inflater;
    private int flag;

    private OnItemClickListener onItemClickListener;
    private OnLongItemClickListener onLongItemClickListener;

    public SuperAdapter(Context context, List<T> list) {

        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.flag = -1;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return setItemLayout(this.list.get(position));
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return super.getViewTypeCount();
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = this.inflater.inflate(setItemLayout(this.list.get(position)), parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        setBindData(viewHolder, position, this.flag, this.list.get(position));

        OnClick onClick = new OnClick(position, parent);
        viewHolder.view.setOnClickListener(onClick);
        viewHolder.view.setOnLongClickListener(onClick);

        return convertView;
    }

    /**
     * 抽象setItemLayout方法子类复写获取当前ItemView控件
     *
     * @return
     */
    public abstract int setItemLayout(T data);

    /**
     * 抽象setBindData方法子类复写获取设置绑定控件数据
     *
     * @param viewHolder
     * @param position
     * @param data
     */
    public abstract void setBindData(ViewHolder viewHolder, int position, int flag, T data);

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    /**
     * 添加指定位置数据
     *
     * @param position
     * @param data
     */
    public void add(int position, T data) {
        this.list.add(position, data);
        super.notifyDataSetChanged();
    }

    /**
     * 删除指定位置数据
     *
     * @param position
     */
    public void delete(int position) {
        this.list.remove(position);
        super.notifyDataSetChanged();
    }

    /**
     * 标记指定位置数据
     *
     * @param position
     */
    public void setFlag(int position) {
        this.flag = position;
        super.notifyDataSetChanged();
    }

    /**
     * Item 单击事件接口
     *
     * @author 大好河山清水秀
     */
    public interface OnItemClickListener {

        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    /**
     * Item 长按单击事件接口
     *
     * @author 大好河山清水秀
     */
    public interface OnLongItemClickListener {

        boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id);
    }

    /**
     * Item 点击监听
     *
     * @author 大好河山清水秀
     */
    public class OnClick implements OnClickListener, OnLongClickListener {

        private int position;
        private ViewGroup viewGroup;

        public OnClick(int position, ViewGroup viewGroup) {
            super();
            this.position = position;
            this.viewGroup = viewGroup;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean onLongClick(View v) {
            // TODO Auto-generated method stub
            if (onLongItemClickListener != null) {

                onLongItemClickListener.onItemLongClick((AdapterView<Adapter>) viewGroup, v, position,
                        getItemId(position));

            }
            return false;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick((AdapterView<Adapter>) viewGroup, v, position, getItemId(position));
            }
        }

    }

    /**
     * 数据绑定
     *
     * @author 大好河山清水秀
     */
    public class ViewHolder {

        private View view;
        private SparseArray<View> items;

        public ViewHolder(View view) {

            this.view = view;
            this.items = new SparseArray<View>();

        }

        /**
         * 通过 findViewById 获取控件
         *
         * @param res
         * @return
         */
        @SuppressWarnings({"unchecked", "hiding"})
        private <T extends View> T findViewById(int res) {

            View v = items.get(res);

            if (v == null) {

                v = view.findViewById(res);
                items.put(res, v);
            } else {

                items.put(res, v);
            }

            return (T) v;

        }

        /**
         * 通过 ID 获取View
         *
         * @param res
         * @return
         */
        public View getView(int res) {
            return findViewById(res);
        }

        /**
         * 通过 ID 获取TextView
         *
         * @param res
         * @return
         */
        public TextView getTextView(int res) {
            return (TextView) getView(res);
        }

        /**
         * 通过 ID 获取Button
         *
         * @param res
         * @return
         */
        public Button getButton(int res) {
            return (Button) getView(res);
        }

        /**
         * 通过 ID 获取ToggleButton
         *
         * @param res
         * @return
         */
        public ToggleButton getToogleButton(int res) {
            return (ToggleButton) getView(res);
        }

        /**
         * 通过 ID 获取RadioButton
         *
         * @param res
         * @return
         */
        public RadioButton getRadioButton(int res) {
            return (RadioButton) getView(res);
        }

        /**
         * 通过 ID 获取RadioGroup
         *
         * @param res
         * @return
         */
        public RadioGroup getRadioGroup(int res) {
            return (RadioGroup) getView(res);
        }

        /**
         * 通过 ID 获取ImageButton
         *
         * @param res
         * @return
         */
        public ImageButton getImageButton(int res) {
            return (ImageButton) getView(res);
        }

        /**
         * 通过 ID 获取ImageView
         *
         * @param res
         * @return
         */
        public ImageView getImageView(int res) {
            return (ImageView) getView(res);
        }

        /**
         * 通过 ID 获取EditText
         *
         * @param res
         * @return
         */
        public EditText getEditText(int res) {
            return (EditText) getView(res);
        }

        /**
         * 通过 ID 获取ListView
         *
         * @param res
         * @return
         */
        public ListView getListView(int res) {
            return (ListView) getView(res);
        }

        /**
         * 通过 ID 获取GridView
         *
         * @param res
         * @return
         */
        public GridView getGridView(int res) {
            return (GridView) getView(res);
        }
    }

}
