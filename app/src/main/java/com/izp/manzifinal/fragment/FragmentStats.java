package com.izp.manzifinal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.izp.manzifinal.R;
import com.izp.manzifinal.adapter.RecyclerAdapterStats;
import com.izp.manzifinal.model.ItemTodo;
import com.izp.manzifinal.model.ItemWaste;
import com.izp.manzifinal.util.SaveLoadData;
import com.izp.manzifinal.view.EmptySupportRecyclerView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by izp on 2016/2/22.
 */
public class FragmentStats extends Fragment{
    private View statsView;
    private EmptySupportRecyclerView emptySupportRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapterStats recyclerAdapterStats;
    private SaveLoadData saveLoadTodo,saveLoadWaste;
    private ArrayList<ItemWaste> itemWastes;
    private ArrayList<ItemTodo> itemTodos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        statsView = inflater.inflate(R.layout.fragment_stats,container,false);
        return statsView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        saveLoadTodo = new SaveLoadData(FragmentTodo.FILENAME,getActivity());
        saveLoadWaste = new SaveLoadData(getActivity(),FragmentWaste.WASTE_FILENAME);
        itemTodos = getLocalTodo(saveLoadTodo);
        itemWastes = getLocalWaste(saveLoadWaste);
        recyclerAdapterStats = new RecyclerAdapterStats(itemTodos,itemWastes);
        emptySupportRecyclerView = (EmptySupportRecyclerView) statsView.findViewById(R.id.RecyclerFragmentStats);
        layoutManager = new GridLayoutManager(getActivity(),2,LinearLayoutManager.VERTICAL,false);
        emptySupportRecyclerView.setEmptyView(getActivity().findViewById(R.id.supportEmptyStats));
        emptySupportRecyclerView.setItemAnimator(new DefaultItemAnimator());
        emptySupportRecyclerView.setHasFixedSize(true);
        emptySupportRecyclerView.setLayoutManager(layoutManager);
        emptySupportRecyclerView.setAdapter(recyclerAdapterStats);
    }
    public static ArrayList<ItemTodo> getLocalTodo(SaveLoadData s){
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
    public static ArrayList<ItemWaste> getLocalWaste(SaveLoadData s){
//        Log.d("vicky","看获取系数据的自定义方法运行没有");
        ArrayList<ItemWaste> ItemWastes = null;
        try {
            ItemWastes = s.loadWaste();//获取到已经保存的集合
//            Log.d("vicky","getLocalDate方法中的itemTodos"+ItemWastes.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ItemWastes == null){
            ItemWastes = new ArrayList<>();//如果之前没有保存元素，那么就新建一个集合
        }
        return ItemWastes;
    }
    @Override
    public void onPause() {
        super.onPause();
    }
}
