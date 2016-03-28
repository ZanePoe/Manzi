package com.izp.manzifinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.izp.manzifinal.fragment.FragmentWaste;
import com.izp.manzifinal.model.ItemWaste;
import com.izp.manzifinal.util.SaveLoadData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.next.tagview.TagCloudView;

/**
 * Created by izp on 2016/2/24.
 */
public class AddWasteActivity extends AppCompatActivity {
    private Toolbar toolbarWaste;
    private ItemWaste itemWaste;
    private EditText titleWasteEt,notesWasteEt;
    private TextView showWasteWeight;
    private FloatingActionButton fabAddWaste;
    private TagCloudView tagCloudView;
    private String oldWasteTitleEt,oldWasteNotes;
    private Long oldWasteCreateTime;
    private boolean mark =true;
    private SeekBar weightSeekBar;
    private int oldWasteWeight;
    private SaveLoadData saveLoadData;
    private List<String> tags;
    private ArrayList<ItemWaste> itemWastes;


    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("H时m分");



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_waste);
        toolbarWaste = (Toolbar) findViewById(R.id.toolbarWaste);
        setSupportActionBar(toolbarWaste);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setElevation(3);
            getSupportActionBar().setDisplayShowTitleEnabled(false);//
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setHomeAsUpIndicator();我这里使用默认的，不准备自己设置返回按钮
        }
        toolbarWaste.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        itemWaste = (ItemWaste) getIntent().getSerializableExtra(FragmentWaste.WASTE_ITEM);
        initView();//从传入的item中得到以前保存的数据，并设置到当前view
        listenNewData();//开启所有控件的监听事件，获取到改变的值
        fabAddWaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toResult(1);
                if (mark){
                    finish();
                }

            }
        });


    }

    private void saveNewData() {
        itemWaste.setWasteName(oldWasteTitleEt);
//        Log.d("vicky1","传递的值是否正常"+oldWasteTitleEt);

        itemWaste.setWasteWeight(oldWasteWeight);
        itemWaste.setWasteNotes(oldWasteNotes);
//        Log.d("vicky1","传递的值是否正常"+oldWasteWeight);
        if (itemWaste.getWasteCreateDate()==null){
            itemWaste.setWasteCreateDate(new Date().getTime());
        }

//        itemWaste.setWasteName(itemTodo.getToDoColor());

    }
    private void initView() {

        oldWasteTitleEt = itemWaste.getWasteName();
        oldWasteCreateTime = itemWaste.getWasteCreateDate();
        oldWasteWeight = itemWaste.getWasteWeight();
        oldWasteNotes = itemWaste.getWasteNotes();

        titleWasteEt = (EditText) findViewById(R.id.titleWasteEt);
        tagCloudView = (TagCloudView) findViewById(R.id.tagCloudView);
        weightSeekBar = (SeekBar) findViewById(R.id.weightWasteSeekBar);
        fabAddWaste = (FloatingActionButton) findViewById(R.id.fabAddWaste);
        showWasteWeight = (TextView) findViewById(R.id.showWasteWeight);
        notesWasteEt= (EditText) findViewById(R.id.notesWasteEt);

        titleWasteEt.requestFocus();
        titleWasteEt.setText(oldWasteTitleEt);
        weightSeekBar.setProgress(oldWasteWeight);
        showWasteWeight.setText(String.valueOf(oldWasteWeight));
        notesWasteEt.setText(oldWasteNotes);
        tags = new ArrayList<>();
        saveLoadData = new SaveLoadData(this,FragmentWaste.WASTE_FILENAME);
        itemWastes = getLocalWaste(saveLoadData);
        for (ItemWaste itemWaste : itemWastes){
            tags.add(itemWaste.getWasteName());
        }
        tagCloudView.setTags(tags);
    }
    private void listenNewData() {
        //监听标题的生成
        titleWasteEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                oldWasteTitleEt = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //监听tag的选择
        tagCloudView.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onTagClick(int position) {
                if (position == -1) {
                    tagCloudView.singleLine(false);
                } else {
                    oldWasteTitleEt = tags.get(position);
                    titleWasteEt.setText(oldWasteTitleEt);
                }

            }
        });


        //监听进度的改变

        //监听分值的设定
        weightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                oldWasteWeight = progress;
                showWasteWeight.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        notesWasteEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                oldWasteNotes = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }




    public void toResult(int resultCode){
        //内容校验
        if (oldWasteTitleEt.length()==0||oldWasteTitleEt==null){
            Toast toast = Toast.makeText(AddWasteActivity.this,"主人，您忘了填写任务或计划的名称了啦！",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,-80);
            toast.show();
            mark =false;
        }
        //校验完就保存给item实例
        if (mark){
            saveNewData();
            Intent resultIntent = new Intent();
            resultIntent.putExtra(FragmentWaste.WASTE_ITEM,itemWaste);
//        Log.d("vicky2",itemTodo.getToDoName());
            setResult(resultCode,resultIntent);
//            Log.d("vicky2",itemWaste.getWasteName());
        }


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
    public void onBackPressed() {

        toResult(1);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.delete_menu:
                Intent resultIntent = new Intent();
                resultIntent.putExtra(FragmentWaste.WASTE_ITEM,itemWaste);
                setResult(-1,resultIntent);
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
