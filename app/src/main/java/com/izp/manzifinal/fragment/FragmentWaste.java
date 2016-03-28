package com.izp.manzifinal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.izp.manzifinal.AddWasteActivity;
import com.izp.manzifinal.MainActivity;
import com.izp.manzifinal.R;
import com.izp.manzifinal.adapter.RecyclerAdapterWaste;
import com.izp.manzifinal.model.ItemWaste;
import com.izp.manzifinal.util.HidingScrollListener;
import com.izp.manzifinal.util.MyItemTouchHelper;
import com.izp.manzifinal.util.SaveLoadData;
import com.izp.manzifinal.view.EmptySupportRecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by izp on 2016/2/22.
 */
public class FragmentWaste extends Fragment implements View.OnClickListener, RecyclerAdapterWaste.OnItemClickListener {
    private View wasteView;
    private EmptySupportRecyclerView wasteRecycler;
    private RecyclerView.LayoutManager wasteLayoutManager;
//    private FloatingActionButton fabWaste;
    private ArrayList<ItemWaste> itemWastes;
    private SaveLoadData saveLoadData;
    private RecyclerAdapterWaste recyclerAdapterWaste;
    public ItemTouchHelper itemTouchHelper;
    private int itemPosition;
    public static final String WASTE_ITEM = "manzi.itemWaste";
    public static final String WASTE_FILENAME = "itemWaste.json";//保存数据的文件名,但不知道保存在哪儿？//保存在系统data文件夹中

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        wasteView = inflater.inflate(R.layout.fragment_waste,container,false);
        return wasteView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        saveLoadData = new SaveLoadData(getActivity(),WASTE_FILENAME);
        itemWastes = getLocalWaste(saveLoadData);
        recyclerAdapterWaste = new RecyclerAdapterWaste(itemWastes);

        wasteRecycler = (EmptySupportRecyclerView) wasteView.findViewById(R.id.RecyclerFragmentWaste);
        wasteLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
//        fabWaste = (FloatingActionButton) wasteView.findViewById(R.id.fabWaste);//与FragmentTodo获取控件的方式有点区别
//        fabWaste.setOnClickListener(this);
        recyclerAdapterWaste.setOnItemClickListener(this);
        wasteRecycler.setEmptyView(getActivity().findViewById(R.id.supportEmptyWaste));
        wasteRecycler.setItemAnimator(new DefaultItemAnimator());
        wasteRecycler.setLayoutManager(wasteLayoutManager);
        final MainActivity mainActivity = (MainActivity) getActivity();
        wasteRecycler.addOnScrollListener(new HidingScrollListener() {
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
        ItemTouchHelper.Callback callback = new MyItemTouchHelper(recyclerAdapterWaste);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(wasteRecycler);
        wasteRecycler.setAdapter(recyclerAdapterWaste);
    }
    public ArrayList<ItemWaste> getLocalWaste(SaveLoadData saveLoadData){
        ArrayList<ItemWaste> itemWastes = null;
        try {
            itemWastes = saveLoadData.loadWaste();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (itemWastes ==null){
            itemWastes = new ArrayList<>();
        }
        return  itemWastes;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), AddWasteActivity.class);
        ItemWaste itemWaste = new ItemWaste("",0,"");
        intent.putExtra(WASTE_ITEM,itemWaste);
        startActivityForResult(intent,200);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode ==200&&resultCode ==1){
            ItemWaste itemWaste = (ItemWaste) data.getSerializableExtra(WASTE_ITEM);
            boolean existed = false;
            for (int i=0;i<itemWastes.size();i++){
                if (itemWaste.getWasteID().equals(itemWastes.get(i).getWasteID())){
                    itemWastes.set(i,itemWaste);
                    existed = true;
                    recyclerAdapterWaste.notifyDataSetChanged();
                    break;
                }
            }
            if (!existed){
                itemWastes.add(itemWaste);
                recyclerAdapterWaste.notifyDataSetChanged();
                recyclerAdapterWaste.notifyItemInserted(itemWastes.size()-1);
            }
        }else if (resultCode == -1 && requestCode ==200){
            ItemWaste itemWaste = (ItemWaste) data.getSerializableExtra(WASTE_ITEM);
//            itemWastes.remove(itemWaste);
//            recyclerAdapterWaste.notifyItemRemoved(itemPosition);
            recyclerAdapterWaste.itemWastes.remove(itemPosition);
            recyclerAdapterWaste.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            saveLoadData.saveWaste(itemWastes);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(),AddWasteActivity.class);
        ItemWaste itemWaste = itemWastes.get(position);
        intent.putExtra(WASTE_ITEM,itemWaste);
        itemPosition = position;
        startActivityForResult(intent,200);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
