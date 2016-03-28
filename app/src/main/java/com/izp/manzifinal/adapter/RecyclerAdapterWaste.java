package com.izp.manzifinal.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.izp.manzifinal.R;
import com.izp.manzifinal.holder.ViewHolderTodo;
import com.izp.manzifinal.holder.ViewHolderWaste;
import com.izp.manzifinal.model.ItemTodo;
import com.izp.manzifinal.model.ItemWaste;
import com.izp.manzifinal.util.MyItemTouchHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by izp on 2016/2/22.
 */
public class RecyclerAdapterWaste extends RecyclerView.Adapter<ViewHolderWaste> implements MyItemTouchHelper.ItemTouchHelperAdapter{
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("H时m分");
    //目前只实现了它的点击事件在Holder中，但是滑动事件需要自定义接口
    public ArrayList<ItemWaste> itemWastes;//这是需要在这里初始化的数据展示
    public OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public RecyclerAdapterWaste(ArrayList<ItemWaste> itemWastes) {
        this.itemWastes = itemWastes;
    }
    @Override
    public ViewHolderWaste onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_waste,parent,false);
        /*还有一种写法：
        *在构造方法中传入context，然后
        * mLayoutInflater = LayoutInflater.from(mContext);
        * View mView = mLayoutInflater.inflate(R.layout.item_main, parent, false);
        * */
        return new ViewHolderWaste(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderWaste holder, final int position) {
        //从传入的集合中获取对应位置的item
        final ItemWaste itemWaste = itemWastes.get(position);
        holder.titleItemWaste.setText(itemWaste.getWasteName());
        holder.createTimeItemWaste.setText(dateFormat.format(new Date(itemWaste.getWasteCreateDate())));
        holder.showWeightItemWaste.setText(String.valueOf(itemWaste.getWasteWeight()));
        //要在见一个RecyclerAdapter用来充实步骤，然后使用getCont方法获取总步数，
        // 并记录完成了的步骤的个数，实现滑动事件，右划变成完成状态并设置背景颜色，
        // 再次右划又切换回当前状态，左划即删除
        TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(itemWaste.getWasteName().substring(0,1),itemWaste.getWasteColor());
        //绘制圆形状态图
        holder.circleImageWaste.setImageDrawable(textDrawable);
        //本item不显示还差几天到deadLine，直接用颜色表示了。

        holder.wasteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    holder.wasteRemarkView.setVisibility(View.VISIBLE);
                    TextView wasteRemarkText = (TextView) holder.itemView.findViewById(R.id.wasteRemarkText);
                    wasteRemarkText.setText(itemWaste.getWasteNotes());
                    holder.relativeLayout.setBackgroundColor(Color.LTGRAY);
                }else {
                    holder.wasteRemarkView.setVisibility(View.GONE);
                    holder.relativeLayout.setBackgroundColor(Color.WHITE);
                }
            }
        });

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
        return itemWastes.size();
    }

    @Override
    public void onItemMoved(int fromPosition, int toPosition) {
        if(fromPosition<toPosition){
            for(int i=fromPosition; i<toPosition; i++){
                Collections.swap(itemWastes, i, i+1);
            }
        }
        else{
            for(int i=fromPosition; i > toPosition; i--){
                Collections.swap(itemWastes, i, i-1);
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
