package com.izp.manzifinal.holder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.izp.manzifinal.R;

/**
 * Created by izp on 2016/2/22.
 */
public class ViewHolderTodo extends RecyclerView.ViewHolder {
    public View view;//default权限只能保证在一个包里的程序可以访问，现在我们建立了几个分开的包，所以要用public
    public RelativeLayout relativeLayout;
    public ImageView circleImageTodo;
    public TextView titleItemTodo,progressItemTodo,createTimeItemTodo,showProgressItemTodo,
            todoRemark,todoRemarkText,weightItemTodo,showWeightItemTodo;
    public SwitchCompat remarkSwitch;
    public ViewStub todoRemarkView;

    public ViewHolderTodo(View itemView) {
        super(itemView);
        view = itemView;
        /*当这个recyclerView的item被点击之后的事件，点击事件被回调给了*/
        relativeLayout = (RelativeLayout) itemView.findViewById(R.id.contentItemTodo);
        circleImageTodo = (ImageView) itemView.findViewById(R.id.circleImageTodo);
        titleItemTodo = (TextView) itemView.findViewById(R.id.titleItemTodo);
        createTimeItemTodo = (TextView) itemView.findViewById(R.id.createTimeItemTodo);
        progressItemTodo = (TextView) itemView.findViewById(R.id.progressItemTodo);
        showProgressItemTodo = (TextView) itemView.findViewById(R.id.showProgressItemTodo);
        weightItemTodo = (TextView) itemView.findViewById(R.id.weightItemTodo);
        showWeightItemTodo = (TextView) itemView.findViewById(R.id.showWeightItemTodo);
        todoRemark = (TextView) itemView.findViewById(R.id.todoRemark);
        remarkSwitch = (SwitchCompat) itemView.findViewById(R.id.remarkSwitch);
        todoRemarkView = (ViewStub) itemView.findViewById(R.id.todoRemarkView);
    }
}
