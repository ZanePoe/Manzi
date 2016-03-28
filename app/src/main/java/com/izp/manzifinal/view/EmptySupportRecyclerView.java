package com.izp.manzifinal.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by izp on 2016/2/22.
 * /*经过重写的RecyclerView，可以显示为空的状态
 */
public class EmptySupportRecyclerView extends RecyclerView {
    public EmptySupportRecyclerView(Context context) {
        super(context);
    }
    public EmptySupportRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EmptySupportRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private View emptyView;
    //AdapterDataObserver是适配器数据观察者，用来监视RecyclerView的数据变化
    private AdapterDataObserver dataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            showEmpty();//只要有数据改变，判断需不需要显示空View
        }


        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            showEmpty();//每插入一个数据，判断需不需要显示空View
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            showEmpty();//每插入删除一组数据，判断需不需要显示空View
        }
    };

    private void showEmpty() {
        Adapter<?> adapter = getAdapter();//从recyclerView那里获取适配器
        if (adapter!=null && emptyView !=null){//如果数据适配器和view都不为空，也就是说，都进行了初始化
            if (adapter.getItemCount()==0){//如果数据适配器里面的item数为0
                emptyView.setVisibility(VISIBLE);//把可见设置为空布局
                EmptySupportRecyclerView.this.setVisibility(GONE);//然后让item的布局消失
            }else {//只有item数量不为空，那么就显示item把空布局去掉
                emptyView.setVisibility(GONE);
                EmptySupportRecyclerView.this.setVisibility(VISIBLE);
            }
        }

    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter!=null){//如果适配器不为空
            adapter.registerAdapterDataObserver(dataObserver);//就注册适配器数据监听器
            dataObserver.onChanged();//监听数据的改变
        }
    }
    public void setEmptyView(View view){
        emptyView = view;
    }
}
