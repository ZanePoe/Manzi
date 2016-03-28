package com.izp.manzifinal.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.izp.manzifinal.AddTodoActivity;
import com.izp.manzifinal.MainActivity;
import com.izp.manzifinal.R;
import com.izp.manzifinal.adapter.RecyclerAdapterTodo;
import com.izp.manzifinal.model.ItemTodo;
import com.izp.manzifinal.service.TodoNotificationService;
import com.izp.manzifinal.util.HidingScrollListener;
import com.izp.manzifinal.util.MyItemTouchHelper;
import com.izp.manzifinal.util.SaveLoadData;
import com.izp.manzifinal.view.EmptySupportRecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by izp on 2016/2/22.
 */
public class FragmentTodo extends Fragment implements RecyclerAdapterTodo.OnItemClickListener, View.OnClickListener {
    private View todoView;
    private EmptySupportRecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapterTodo recyclerAdapterTodo;
    private ArrayList<ItemTodo> itemTodos;
    private SaveLoadData saveLoadData;
    private int itemPosition;
    public ItemTouchHelper itemTouchHelper;

    public static final String TODO_ITEM = "manzi.itemtodo";
    public static final String FILENAME = "itemstodo.json";//保存数据的文件名,但不知道保存在哪儿？//保存在系统data文件夹中
    public static final String DATA_SET_CHANGED ="manzi.dataSetChanged";
    public static final String CHANGE_HAPPEN = "manzi.changeHappen";
    public static final int RESULT_TODO = 100;
    public static final int RESULT_CANCELED  = 0;
    public static final String TODO_ID = "ID";

    @Override
    public void onResume() {
        Log.d("vicky_FragmentTodo","看集合为空到底是在哪个生命周期中出的问题onResume");
        super.onResume();
//        saveLoadData = new SaveLoadData(FILENAME,getActivity());
//        itemTodos = getLocalDate(saveLoadData);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        todoView = inflater.inflate(R.layout.fragment_todo,container,false);

        return todoView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("vicky_FragmentTodo","看集合为空到底是在哪个生命周期中出的问题onResume");
        super.onActivityCreated(savedInstanceState);
        saveLoadData = new SaveLoadData(FILENAME,getActivity());
        itemTodos = getLocalDate(saveLoadData);
        if (itemTodos.size()>0){
        }
        recyclerAdapterTodo = new RecyclerAdapterTodo(itemTodos);
        recyclerView = (EmptySupportRecyclerView) todoView.findViewById(R.id.RecyclerFragmentTodo);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        recyclerAdapterTodo.setOnItemClickListener(this);
        recyclerView.setEmptyView(getActivity().findViewById(R.id.supportEmpty));
//        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);

//        recyclerView.setOnScrollListener(hidingScrollListener);
        final MainActivity mainActivity = (MainActivity) getActivity();
        recyclerView.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void show() {
                //tanslation是平移的意思，设置插值器，decelerate减速插值器
                mainActivity.fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void hide() {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)mainActivity.fab.getLayoutParams();
                int fabMargin = lp.bottomMargin;//获取底部边距
                //Accelerate加速插值器
                mainActivity.fab.animate().translationY(mainActivity.fab.getHeight()+fabMargin).setInterpolator(new AccelerateInterpolator(2.0f)).start();
            }
        });
        ItemTouchHelper.Callback callback = new MyItemTouchHelper(recyclerAdapterTodo);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(recyclerAdapterTodo);
        setNotification();
    }


    public static ArrayList<ItemTodo> getLocalDate(SaveLoadData s){
//        Log.d("vicky","看获取系数据的自定义方法运行没有");
        ArrayList<ItemTodo> itemTodos = null;
        try {
            itemTodos = s.loadTodo();//获取到已经保存的集合
//            Log.d("vicky","getLocalDate方法中的itemTodos"+itemTodos.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (itemTodos == null){
            itemTodos = new ArrayList<>();//如果之前没有保存元素，那么就新建一个集合
        }
        return itemTodos;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), AddTodoActivity.class);
        ItemTodo itemTodo = itemTodos.get(position);
        intent.putExtra(TODO_ITEM,itemTodo);
        getActivity().startActivityForResult(intent,100);
//        startActivityForResult(intent,100);
        itemPosition = position;
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), AddTodoActivity.class);
        ItemTodo itemTodo = new ItemTodo("",0,null,0,"");
        intent.putExtra(TODO_ITEM,itemTodo);
        startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("vicky-FragmentTodo","检查Result返回没有"+itemTodos.size());
        if (resultCode== 1 && requestCode == 100){
            ItemTodo itemTodo = (ItemTodo) data.getSerializableExtra(TODO_ITEM);
            boolean existed = false;
            for (int i = 0 ; i<itemTodos.size();i++){
                if (itemTodo.getToDoID().equals(itemTodos.get(i).getToDoID())){
                    itemTodos.set(i,itemTodo);
                    existed = true;
                    recyclerAdapterTodo.notifyDataSetChanged();
                    break;
                }
            }
            if (!existed){
                itemTodos.add(itemTodo);
//                recyclerAdapterTodo.itemTodos.add(itemTodo);
                recyclerAdapterTodo.notifyDataSetChanged();
                recyclerAdapterTodo.notifyItemInserted(itemTodos.size()-1);

            }
        }else if (resultCode == -1 && requestCode == 100){
            ItemTodo itemTodo = (ItemTodo) data.getSerializableExtra(TODO_ITEM);
//            Log.d("vicky-fragmentTodo1","回传但还没删时"+itemTodos.size());

//            Log.d("vicky-fragmentTodo2","看一下到底删除了没有"+itemTodos.size());
            recyclerAdapterTodo.itemTodos.remove(itemPosition);
            recyclerAdapterTodo.notifyDataSetChanged();
            //            itemTodos.remove(itemTodo);
//            recyclerAdapterTodo.notifyItemRemoved(itemPosition);//
        }
    }

    private void addToData(ItemTodo itemtodo) {


    }

    @Override
    public void onPause() {
        super.onPause();
        try {
//            Log.d("vicky-fragmentTodo3","看一下到底删除了没有"+itemTodos.size());
            saveLoadData.saveTodo(itemTodos);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setNotification(){
        if (itemTodos!=null){
            for (ItemTodo itemTodo :itemTodos){
                if (itemTodo.getToDoDeadline()!=null&& new Date(itemTodo.getToDoDeadline()).after(new Date())){
                    Intent notifyIntent = new Intent(getActivity(), TodoNotificationService.class);
                    notifyIntent.putExtra(TODO_ID,itemTodo.getToDoID());
                    notifyIntent.putExtra(TODO_ITEM,itemTodo.getToDoName());
                    createAlarm(notifyIntent,itemTodo.getToDoID().hashCode(),itemTodo.getToDoDeadline());//第一次设置提醒
                }
            }
        }
    }
    public void createAlarm(Intent intent, int requestCode, long time){
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(getActivity(),requestCode,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);
    }
    public void deleteAlerm(Intent intent,int requestCode){
        if (PendingIntent.getService(getActivity(),requestCode,intent,PendingIntent.FLAG_NO_CREATE)!=null){
            PendingIntent pendingIntent = PendingIntent.getService(getActivity(),requestCode,intent,PendingIntent.FLAG_NO_CREATE);
            pendingIntent.cancel();
            ((AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE)).cancel(pendingIntent);
        }
    }

}
