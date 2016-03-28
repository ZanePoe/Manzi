package com.izp.manzifinal.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.izp.manzifinal.R;
import com.izp.manzifinal.holder.ViewHolderTodo;
import com.izp.manzifinal.model.ItemTodo;
import com.izp.manzifinal.util.MyItemTouchHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by izp on 2016/2/22.
 */
public class RecyclerAdapterTodo extends RecyclerView.Adapter<ViewHolderTodo> implements MyItemTouchHelper.ItemTouchHelperAdapter{


    //目前只实现了它的点击事件在Holder中，但是滑动事件需要自定义接口
    public ArrayList<ItemTodo> itemTodos;//这是需要在这里初始化的数据展示
    public OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RecyclerAdapterTodo(ArrayList<ItemTodo> itemTodos) {
        this.itemTodos = itemTodos;
    }

    @Override
    public ViewHolderTodo onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo,parent,false);
        /*还有一种写法：
        *在构造方法中传入context，然后
        * mLayoutInflater = LayoutInflater.from(mContext);
        * View mView = mLayoutInflater.inflate(R.layout.item_main, parent, false);
        * */
        return new ViewHolderTodo(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderTodo holder, final int position) {
        //从传入的集合中获取对应位置的item
        final ItemTodo itemTodo = itemTodos.get(position);
        holder.titleItemTodo.setText(itemTodo.getToDoName());
//        holder.createTimeItemTodo.setText((CharSequence) itemTodo.getToDoCreateDate());
        holder.createTimeItemTodo.setText(new SimpleDateFormat("yyyy年M月d日 hh时mm分").format(itemTodo.getToDoCreateDate()));
        holder.showProgressItemTodo.setText(itemTodo.getToDoProgress()+"%");
        holder.showWeightItemTodo.setText(String.valueOf(itemTodo.getToDoWeight()*itemTodo.getToDoProgress()/100));
        //要在见一个RecyclerAdapter用来充实步骤，然后使用getCont方法获取总步数，
        // 并记录完成了的步骤的个数，实现滑动事件，右划变成完成状态并设置背景颜色，
        // 再次右划又切换回当前状态，左划即删除
        TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(String.valueOf((itemTodo.getToDoDeadline()-new Date().getTime())/(24*60*60*1000)),itemTodo.getToDoColor());
        //绘制圆形状态图
        holder.circleImageTodo.setImageDrawable(textDrawable);
        holder.remarkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    holder.todoRemarkView.setVisibility(View.VISIBLE);
                    TextView todoRemarkText = (TextView) holder.itemView.findViewById(R.id.todoRemarkText);
                    todoRemarkText.setText(itemTodo.getHasNotes());
                    holder.relativeLayout.setBackgroundColor(Color.LTGRAY);
                }else {
                    holder.todoRemarkView.setVisibility(View.GONE);
                    holder.relativeLayout.setBackgroundColor(Color.WHITE);
                }
            }
        });
        //本item不显示还差几天到deadLine，直接用颜色表示了。
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(holder.itemView,position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onItemLongClick(holder.itemView,position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemTodos.size();
    }

    @Override
    public void onItemMoved(int fromPosition, int toPosition) {
        if(fromPosition<toPosition){
            for(int i=fromPosition; i<toPosition; i++){
                Collections.swap(itemTodos, i, i+1);
            }
        }
        else{
            for(int i=fromPosition; i > toPosition; i--){
                Collections.swap(itemTodos, i, i-1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }


    /*自定义fragment中item的点击事件*/
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
}
