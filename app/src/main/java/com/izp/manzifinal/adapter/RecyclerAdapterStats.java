package com.izp.manzifinal.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.izp.manzifinal.R;
import com.izp.manzifinal.holder.ViewHolderStats;
import com.izp.manzifinal.holder.ViewHolderTodo;
import com.izp.manzifinal.model.ItemTodo;
import com.izp.manzifinal.model.ItemWaste;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by izp on 2016/2/22.
 */
public class RecyclerAdapterStats extends RecyclerView.Adapter<ViewHolderStats>{
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("H时m分");
    private ArrayList<ItemWaste> itemWastes;
    private ArrayList<ItemTodo> itemTodos;
    private int todoDayValue = 0,todoMonthValue = 0,todoWeekValue=0,wasteYesterdayValue=0,
            wasteDayValue = 0,wasteMonthValue = 0,wasteWeekValue=0,todoYesterdayValue=0;
    private int todoDayCount = 0,todoMonthCount = 0,todoWeekCount=0,wasteYesterdayCount=0,
            wasteDayCount = 0,wasteMonthCount = 0,wasteWeekCount=0,todoYesterdayCount=0;

    //目前只实现了它的点击事件在Holder中，但是滑动事件需要自定义接口
    public RecyclerAdapterStats(ArrayList<ItemTodo> itemTodos ,ArrayList<ItemWaste> itemWastes) {
        this.itemTodos = itemTodos;
        this.itemWastes = itemWastes;
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        for(ItemTodo itemTodo:itemTodos){
            calendar.setTimeInMillis(itemTodo.getToDoCreateDate());
            yesterday.setTimeInMillis(itemTodo.getToDoCreateDate()-(24*60*60*1000));
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int week = calendar.get(Calendar.WEEK_OF_MONTH);
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            if (year==today.get(Calendar.YEAR) && month== today.get(Calendar.MONTH) &&
                    week==today.get(Calendar.WEEK_OF_MONTH) && day==today.get(Calendar.DAY_OF_WEEK)){
                todoDayValue = itemTodo.getToDoWeight()*itemTodo.getToDoProgress()/100+todoDayValue;
                todoDayCount++;
            }
            if (year==today.get(Calendar.YEAR) && month== today.get(Calendar.MONTH) &&
                    week==today.get(Calendar.WEEK_OF_MONTH) && day==yesterday.get(Calendar.DAY_OF_WEEK)){
                todoYesterdayValue = itemTodo.getToDoWeight()*itemTodo.getToDoProgress()/100+todoYesterdayValue;
                todoYesterdayCount++;
            }
            if (year==today.get(Calendar.YEAR) && month== today.get(Calendar.MONTH) &&
                    week==today.get(Calendar.WEEK_OF_MONTH)){
                todoWeekValue = itemTodo.getToDoWeight()*itemTodo.getToDoProgress()/100+todoWeekValue;
                todoWeekCount++;
            }
            if (year==today.get(Calendar.YEAR) && month== today.get(Calendar.MONTH)){
                todoMonthValue =itemTodo.getToDoWeight()*itemTodo.getToDoProgress()/100+todoMonthValue;
                todoMonthCount++;
            }
        }
        for (ItemWaste itemWaste : itemWastes){
            Log.d("计次","堕落次数"+itemWastes.size());
            calendar.setTimeInMillis(itemWaste.getWasteCreateDate());
            yesterday.setTimeInMillis(itemWaste.getWasteCreateDate()-(24*60*60*1000));
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int week = calendar.get(Calendar.WEEK_OF_MONTH);
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            if (year==today.get(Calendar.YEAR) && month== today.get(Calendar.MONTH) &&
                    week==today.get(Calendar.WEEK_OF_MONTH) && day==today.get(Calendar.DAY_OF_WEEK)){
                wasteDayValue = itemWaste.getWasteWeight()+wasteDayValue;
                wasteDayCount++;
            }
            if (year==today.get(Calendar.YEAR) && month== today.get(Calendar.MONTH) &&
                    week==today.get(Calendar.WEEK_OF_MONTH) && day==yesterday.get(Calendar.DAY_OF_WEEK)){
                wasteYesterdayValue = itemWaste.getWasteWeight()+wasteYesterdayValue;
                wasteYesterdayCount++;
            }
            if (year==today.get(Calendar.YEAR) && month== today.get(Calendar.MONTH) &&
                    week==today.get(Calendar.WEEK_OF_MONTH)){
                wasteWeekValue = itemWaste.getWasteWeight()+wasteWeekValue;
                wasteWeekCount++;
            }
            if (year==today.get(Calendar.YEAR) && month== today.get(Calendar.MONTH)){
                wasteMonthValue =itemWaste.getWasteWeight()+wasteMonthValue;
                wasteMonthCount++;
            }
        }
    }
    @Override
    public ViewHolderStats onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stats,parent,false);
        /*还有一种写法：
        *在构造方法中传入context，然后
        * mLayoutInflater = LayoutInflater.from(mContext);
        * View mView = mLayoutInflater.inflate(R.layout.item_main, parent, false);
        * */
        return new ViewHolderStats(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderStats holder, int position) {

        if (position%2==0){
            //偶数从0开始，的设置为任务的个数和分数
            holder.count.setText("任务数量:");
            holder.progressItemStats.setText("获得分值");
            if (position==0){
                TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                        .textColor(Color.WHITE)
                        .useFont(Typeface.DEFAULT)
                        .toUpperCase()
                        .endConfig()
                        .buildRound("今",Color.BLUE);
                //绘制圆形状态图
                holder.circleImageStats.setImageDrawable(textDrawable);
                Log.d("计次",""+todoDayCount);
                holder.showCount.setText(String.valueOf(todoDayCount));
                holder.showProgressStats.setText(String.valueOf(todoDayValue));
            }else if (position==2){
                TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                        .textColor(Color.WHITE)
                        .useFont(Typeface.DEFAULT)
                        .toUpperCase()
                        .endConfig()
                        .buildRound("昨",Color.CYAN);
                //绘制圆形状态图
                holder.circleImageStats.setImageDrawable(textDrawable);
                holder.showCount.setText(String.valueOf(todoYesterdayCount));
                holder.showProgressStats.setText(String.valueOf(todoYesterdayValue));
            }else if (position==4){
                TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                        .textColor(Color.WHITE)
                        .useFont(Typeface.DEFAULT)
                        .toUpperCase()
                        .endConfig()
                        .buildRound("周",Color.GREEN);
                //绘制圆形状态图
                holder.circleImageStats.setImageDrawable(textDrawable);
                holder.showCount.setText(String.valueOf(todoWeekCount));
                holder.showProgressStats.setText(String.valueOf(todoWeekValue));
            }else if (position==6){
                TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                        .textColor(Color.WHITE)
                        .useFont(Typeface.DEFAULT)
                        .toUpperCase()
                        .endConfig()
                        .buildRound("月",Color.LTGRAY);
                //绘制圆形状态图
                holder.circleImageStats.setImageDrawable(textDrawable);
                holder.showCount.setText(String.valueOf(todoMonthCount));
                holder.showProgressStats.setText(String.valueOf(todoMonthValue));
            }
            
        }else {
            holder.count.setText("腐败次数");
            holder.progressItemStats.setText("消耗分值");
            if (position==1){
                TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                        .textColor(Color.WHITE)
                        .useFont(Typeface.DEFAULT)
                        .toUpperCase()
                        .endConfig()
                        .buildRound("今",Color.DKGRAY);
                //绘制圆形状态图
                holder.circleImageStats.setImageDrawable(textDrawable);
                Log.d("计次",""+wasteDayCount);
                holder.showCount.setText(String.valueOf(wasteDayCount));
                holder.showProgressStats.setText(String.valueOf(wasteDayValue));
            }else if (position==3){
                TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                        .textColor(Color.WHITE)
                        .useFont(Typeface.DEFAULT)
                        .toUpperCase()
                        .endConfig()
                        .buildRound("昨",Color.YELLOW);
                //绘制圆形状态图
                holder.circleImageStats.setImageDrawable(textDrawable);
                holder.showCount.setText(String.valueOf(wasteYesterdayCount));
                holder.showProgressStats.setText(String.valueOf(wasteYesterdayValue));
            }else if (position==5){
                TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                        .textColor(Color.WHITE)
                        .useFont(Typeface.DEFAULT)
                        .toUpperCase()
                        .endConfig()
                        .buildRound("周",Color.GRAY);
                //绘制圆形状态图
                holder.circleImageStats.setImageDrawable(textDrawable);
                holder.showCount.setText(String.valueOf(wasteWeekCount));
                holder.showProgressStats.setText(String.valueOf(wasteWeekValue));
            }else if (position==7){
                TextDrawable textDrawable = TextDrawable.builder().beginConfig()
                        .textColor(Color.WHITE)
                        .useFont(Typeface.DEFAULT)
                        .toUpperCase()
                        .endConfig()
                        .buildRound("月",Color.RED);
                //绘制圆形状态图
                holder.circleImageStats.setImageDrawable(textDrawable);
                holder.showCount.setText(String.valueOf(wasteMonthCount));
                holder.showProgressStats.setText(String.valueOf(wasteMonthValue));
            }
            
        }

    }

    @Override
    public int getItemCount() {
        return 8;
    }
}
