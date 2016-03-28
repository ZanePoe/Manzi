package com.izp.manzifinal.model;

import android.graphics.Color;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by izp on 2016/2/22.
 */
public class ItemTodo implements Serializable{

    private String toDoName;//任务名
    private int toDoWeight;//分值
    private Long toDoDeadline;
    private int toDoProgress;//进度
    private String hasNotes;//是否有步骤
    private UUID toDoID;//这个任务的id



    private Long toDoCreateDate;//创建日期
    private int toDoColor;//任务图标的颜色


    public static final String TO_DO_NAME = "toDoName";
    public static final String TO_DO_WEIGHT = "toDoWeight";
    public static final String TO_D0_DEADLINE = "toDoDeadline";
    public static final String TO_DO_PROGRESS = "toDoProgress";
    public static final String TO_DO_HAS_NOTES = "hasNotes";
    public static final String TO_DO_ID = "toDoID";
    public static final String T0_D0_CREATE_DATE ="doDoCreateDate";

    public ItemTodo(String toDoName, int toDoWeight, Long toDoDeadline, int toDoProgress, String hasNotes) {
        this.toDoName = toDoName;
        this.toDoWeight = toDoWeight;
        this.toDoDeadline = toDoDeadline;
        this.toDoProgress = toDoProgress;
        this.hasNotes = hasNotes;
        this.toDoColor = Color.GREEN;//默认浅绿色
        this.toDoID = UUID.randomUUID();
        this.toDoCreateDate = new Date().getTime();//得到毫秒数

    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TO_DO_NAME,toDoName);
        jsonObject.put(TO_DO_WEIGHT,toDoWeight);
        jsonObject.put(TO_D0_DEADLINE,toDoDeadline);
        jsonObject.put(T0_D0_CREATE_DATE,toDoCreateDate);
        jsonObject.put(TO_DO_PROGRESS,toDoProgress);
        jsonObject.put(TO_DO_ID,toDoID.toString());
        jsonObject.put(TO_DO_HAS_NOTES, hasNotes);
//        jsonObject.put(STEP_ID,stepID);

        return jsonObject;
    }
    public ItemTodo(JSONObject jsonObject) throws JSONException {
        toDoName = jsonObject.getString(TO_DO_NAME);
        toDoWeight = jsonObject.getInt(TO_DO_WEIGHT);
        toDoCreateDate = jsonObject.getLong(T0_D0_CREATE_DATE);
        toDoDeadline = jsonObject.getLong(TO_D0_DEADLINE);
        toDoProgress = jsonObject.getInt(TO_DO_PROGRESS);
        toDoID = UUID.fromString(jsonObject.getString(TO_DO_ID));
        hasNotes = jsonObject.getString(TO_DO_HAS_NOTES);
    }

    public String getToDoName() {
        return toDoName;
    }

    public void setToDoName(String toDoName) {
        this.toDoName = toDoName;
    }

    public int getToDoWeight() {
        return toDoWeight;
    }

    public void setToDoWeight(int toDoWeight) {
        this.toDoWeight = toDoWeight;
    }

    public Long getToDoDeadline() {
        return toDoDeadline;
    }

    public void setToDoDeadline(Long toDoDeadline) {
        this.toDoDeadline = toDoDeadline;
    }

    public int getToDoProgress() {
        return toDoProgress;
    }

    public void setToDoProgress(int toDoProgress) {
        this.toDoProgress = toDoProgress;
    }

    public String getHasNotes() {
        return hasNotes;
    }

    public void setHasNotes(String hasNotes) {
        this.hasNotes = hasNotes;
    }

    public UUID getToDoID() {
        return toDoID;
    }

    public void setToDoID(UUID toDoID) {
        this.toDoID = toDoID;
    }

    public Long getToDoCreateDate() {
        return toDoCreateDate;
    }

    public void setToDoCreateDate(Long toDoCreateDate) {
        this.toDoCreateDate = toDoCreateDate;
    }
    public int getToDoColor() {
        /*以后需要对当前时间和deadLine进行加减运算，从而改变return的颜色，日期操作可以参考bbAid的日期工具类*/
        long dayMilli = toDoDeadline - new Date().getTime();
        int day = (int) (dayMilli/(24*60*60*1000));
        if (day<1){
            setToDoColor(Color.BLACK);
        }else if (day<3 && day>=1){
            setToDoColor(0xffff0000);
        }else if (day<5 && day>=3){
            setToDoColor(0xffff00ff);
        }else if (day<7 && day>=5){
            setToDoColor(0xffffff00);
        }else if (day<15 && day>=7){
            setToDoColor(0xff0000ff);
        }else if (day<21 && day>=15){
            setToDoColor(0xff00ffff);
        }else if (day<30 && day>=21){
            setToDoColor(0xff00ffff);
        }else if (day<60 && day>=30){
            setToDoColor(0xFF444444);
        }else {
            setToDoColor(0xFFCCCCCC);
        }
        return toDoColor;
    }

    public void setToDoColor(int toDoColor) {
        this.toDoColor = toDoColor;
    }
}
