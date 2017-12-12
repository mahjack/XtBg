package com.sdzx.xtbg.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Author：Mark
 * Date：2016/3/17 0017
 * Tell：15006330640
 */
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleAdapter.BaseViewHolder>{
    /**
     *  Context 实例
     */
    private Context mContext;

    /**
     *  数据List
     */
    private ArrayList<T> mList;

    /**
     *  item 布局id
     */
    private int mLayoutResId;
    public BaseRecycleAdapter(Context context, ArrayList<T> list, int layoutResId){
        mContext = context;
        mList = list;
        mLayoutResId = layoutResId;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutResId, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseRecycleAdapter.BaseViewHolder holder, int position) {
        T item = mList.get(position);
        bindViews(holder, item);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     *  抽象此方法用于给子类提供holder以及item实例
     * @param holder
     * @param item
     */
    protected abstract void bindViews(BaseViewHolder holder,T item);

    /**
     *  获取 Context
     * @return
     */
    protected Context getContext(){
        return mContext;
    }

    /**
     *  BaseRecycleAdapter 的 ViewHolder
     */
    public class BaseViewHolder extends RecyclerView.ViewHolder{
        /**
         *  itemView
         */
        private View parent;
        /**
         *  统一用于管理View
         */
        private SparseArray<View> sparseArray = new SparseArray<View>();

        /**
         *  构造方法
         * @param itemView
         */
        public BaseViewHolder(View itemView) {
            super(itemView);
            parent = itemView;
        }

        /**
         *  获取 View ，替代findViewById
         * @param resId
         * @return
         */
        public View getView(@IdRes int resId){
            View view = sparseArray.get(resId);
            if(view == null ){
                view = parent.findViewById(resId);
                sparseArray.put(resId,view);
            }
            return view;
        }

        /**
         *  设置TextView内容
         * @param resId
         * @param textRex
         * @return
         */
        public BaseViewHolder setText(@IdRes int resId,int textRex){
            TextView textView = (TextView) getView(resId);
            textView.setText(mContext.getString(textRex));
            return this;
        }

        /**
         *  设置ImageView 本地资源
         * @param resId
         * @param drawableRes
         * @return
         */
        public BaseViewHolder setImageResource(@IdRes int resId,int drawableRes){
            ImageView imageView = (ImageView) getView(resId);
            imageView.setImageResource(drawableRes);
            return this;
        }

        /**
         *  设置 ImageView 网络图片
         * @param resId
         * @param url
         * @return
         */
        public BaseViewHolder loadImage(@IdRes int resId,String url){
            if(url != null && url.length() > 0){
                ImageView imageView = (ImageView) getView(resId);
//                Glide.with(mContext)
//                        .load(url)
//                        .placeholder(android.R.color.transparent)
//                        .error(android.R.color.transparent)
//                        .centerCrop()
//                        .into(imageView);
            }
            return this;
        }

        /**
         *  设置点击事件
         * @param resId
         * @param listener
         * @return
         */
        public BaseViewHolder setOnClickListener(@IdRes int resId,View.OnClickListener listener){
            getView(resId).setOnClickListener(listener);
            return this;
        }

        /**
         *  隐藏控件
         * @param resId
         * @param isVisible
         * @return
         */
        public BaseViewHolder setVisible(@IdRes int resId,boolean isVisible){
            if(isVisible){
                getView(resId).setVisibility(View.VISIBLE);
            }else {
                getView(resId).setVisibility(View.GONE);
            }
            return this;
        }
        public BaseViewHolder setUnread(@IdRes int resId,int unRead){
            Log.e("未读",unRead+"");
            TextView textView = (TextView) getView(resId);
            if(unRead == 0){
                textView.setVisibility(View.GONE);
            }else {
                textView.setText(unRead+"");
                textView.setVisibility(View.VISIBLE);
            }
            return this;
        }
    }
}
