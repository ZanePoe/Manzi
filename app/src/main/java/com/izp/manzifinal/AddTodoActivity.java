package com.izp.manzifinal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.izp.manzifinal.fragment.FragmentTodo;
import com.izp.manzifinal.model.ItemTodo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by izp on 2016/2/24.
 */
public class AddTodoActivity extends AppCompatActivity {
    private Toolbar toolbarTodo;
    private ItemTodo itemTodo;
    private EditText titleEt,dateEt,timeEt, notesEt;
    private TextView showDeadline,showProgress,showWeight,showStep;
    private FloatingActionButton fabAddTodo;
    private String oldTitleEt,oldTimeEt,oldDateEt,oldNotes;
    private boolean tag=true,flag = false;
    private Long oldDeadLine;
    private SeekBar progressSeekBar,weightSeekBar;
    private int oldProgress,oldWeight,color;

    private int newYear,newMouth,newDay,newHour,newMinute;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日"),timeFormat = new SimpleDateFormat("H时m分");
    private RelativeLayout containerAddTodo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        toolbarTodo = (Toolbar) findViewById(R.id.toolbarTodo);
        setSupportActionBar(toolbarTodo);


        if (getSupportActionBar()!=null){
            getSupportActionBar().setElevation(5);
            getSupportActionBar().setDisplayShowTitleEnabled(false);//
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setHomeAsUpIndicator();//我这里使用默认的，不准备自己设置返回按钮
        }

        itemTodo = (ItemTodo) getIntent().getSerializableExtra(FragmentTodo.TODO_ITEM);
        initView();//从传入的item中得到以前保存的数据，并设置到当前view
        listenNewData();//开启所有控件的监听事件，获取到改变的值


        fabAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toResult(1);
                if (tag){
                    finish();
                }
            }
        });


    }
    private void initView() {

        oldTitleEt = itemTodo.getToDoName();
        oldDeadLine = itemTodo.getToDoDeadline();
        if (oldDeadLine!=null&&oldDeadLine!=0){
            oldDateEt = dateFormat.format(new Date(oldDeadLine));
            oldTimeEt = timeFormat.format(new Date(oldDeadLine));
        }
        oldProgress = itemTodo.getToDoProgress();
        oldWeight = itemTodo.getToDoWeight();
        oldNotes = itemTodo.getHasNotes();
        containerAddTodo = (RelativeLayout) findViewById(R.id.containerAddTodo);
        timeEt = (EditText) findViewById(R.id.timeEt);
        dateEt = (EditText) findViewById(R.id.dateEt);
        titleEt = (EditText) findViewById(R.id.titleEt);
        notesEt = (EditText) findViewById(R.id.notesEt);
        progressSeekBar = (SeekBar) findViewById(R.id.progressSeekBar);
        weightSeekBar = (SeekBar) findViewById(R.id.weightSeekBar);
        fabAddTodo = (FloatingActionButton) findViewById(R.id.fabAddTodo);

        showDeadline = (TextView) findViewById(R.id.showDeadline);
        showProgress = (TextView) findViewById(R.id.showProgress);
        showWeight = (TextView) findViewById(R.id.showWeight);

//        titleEt.requestFocus();
        titleEt.setText(oldTitleEt);

        dateEt.setText(oldDateEt);
        timeEt.setText(oldTimeEt);

        progressSeekBar.setProgress(oldProgress);
        showProgress.setText(oldProgress+"%");
        if (oldDeadLine!=null){
            showDeadline.setText((oldDeadLine-new Date().getTime())/(24*60*60*1000)+"天");
        }
        weightSeekBar.setProgress(oldWeight);
        showWeight.setText(String.valueOf(oldWeight));
        notesEt.setText(oldNotes);

    }


    private void listenNewData() {
        //监听标题的生成
        titleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                oldTitleEt = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        containerAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            hideKeyboard(timeEt);
            }
        });
        //监听终止日期的生成
        final Calendar calendar = Calendar.getInstance();

        dateEt.setOnClickListener(new View.OnClickListener() {

            int fYear = calendar.get(Calendar.YEAR);
            int fMonth = calendar.get(Calendar.MONTH);
            int fDay = calendar.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddTodoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateEt.setText(String.format("%d年%d月%d日",year,monthOfYear+1,dayOfMonth));
                        newYear = year;
                        newMouth = monthOfYear;
                        newDay = dayOfMonth;
                        Calendar cal = new GregorianCalendar(newYear,newMouth,newDay,newHour,newMinute);
                        oldDeadLine = cal.getTime().getTime();
                    }
                }, fYear, fMonth, fDay).show();
            }

        });
        timeEt.setOnClickListener(new View.OnClickListener() {
            int fHour = calendar.get(Calendar.HOUR_OF_DAY);
            int fMinute = calendar.get(Calendar.MINUTE);
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddTodoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeEt.setText(String.format("%d时%d分",hourOfDay,minute));
                        newHour = hourOfDay;
                        newMinute = minute;
                        Calendar oldCal = Calendar.getInstance();
                        oldCal.setTimeInMillis(oldDeadLine);
                        newYear = oldCal.get(Calendar.YEAR);
                        newMouth = oldCal.get(Calendar.MONTH);
                        newDay = oldCal.get(Calendar.DAY_OF_MONTH);
                        Calendar cal = new GregorianCalendar(newYear,newMouth,newDay,newHour,newMinute);
                        oldDeadLine = cal.getTime().getTime();
                        showDeadline.setText(String.valueOf((oldDeadLine-new Date().getTime())/(24*60*60*1000))+"天");
                    }
                },fHour,fMinute,true).show();
            }
        });

        //监听进度的改变
        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                oldProgress = progress;
                showProgress.setText(progress+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //监听分值的设定
        weightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                oldWeight = progress;
                showWeight.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //监听备注的生成
        notesEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                oldNotes = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    private void saveNewData() {

        if (oldTitleEt.length()>0){
            itemTodo.setToDoName(oldTitleEt);
        }

        itemTodo.setToDoDeadline(oldDeadLine);
        itemTodo.setToDoProgress(oldProgress);
        itemTodo.setToDoWeight(oldWeight);

        itemTodo.setToDoColor(itemTodo.getToDoColor());

        itemTodo.setHasNotes(oldNotes);
        itemTodo.setToDoColor(color);
    }

    public void hideKeyboard(EditText et){

        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }
    public void toResult(int resultCode){
        //内容校验
        if (oldDeadLine==null|| new Date(oldDeadLine).before(new Date())){
            Toast toast = Toast.makeText(AddTodoActivity.this,"主人，截止日期必须设置且在当前日期之前哦！",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,-80);
            toast.show();
            tag = false;
        }else if (oldTitleEt==null||oldTitleEt.length()==0){
            Toast toast = Toast.makeText(AddTodoActivity.this,"主人，您忘了填写任务或计划的名称了啦！",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,-160);
            toast.show();
            tag=false;
        }else {
            tag=true;
        }

        //校验完就保存给item实例
        if (tag){
            saveNewData();
            Intent resultIntent = new Intent();
            resultIntent.putExtra(FragmentTodo.TODO_ITEM,itemTodo);
            setResult(resultCode,resultIntent);
        }
    }

    @Override
    public void onBackPressed() {
        toResult(1);
        super.onBackPressed();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toobar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.delete_menu:
//                Intent resultIntent = new Intent();
                Intent resultIntent = new Intent();
                resultIntent.putExtra(FragmentTodo.TODO_ITEM,itemTodo);
                setResult(-1,resultIntent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
