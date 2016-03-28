package com.izp.manzifinal.model;

import android.graphics.Color;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by izp on 2016/2/22.
 */
public class ItemWaste implements Serializable{


    private String wasteName;//任务名
    private int wasteWeight;//分值
    private UUID wasteID;//这个任务的id
    private Long wasteCreateDate;//创建日期
    private String wasteNotes;



    private int wasteColor;//任务图标的颜色


    public static final String WASTE_NAME = "wasteName";
    public static final String WASTE_WEIGHT = "wasteWeight";
    public static final String WASTE_ID = "wasteID";
    public static final String WASTE_CREATE_DATE ="wasteCreateDate";
    public static final String WASTE_NOTES = "wasteNotes" ;

    public ItemWaste(String wasteName, int wasteWeight,String wasteNotes) {
        this.wasteName = wasteName;
        this.wasteWeight = wasteWeight;
        this.wasteID = UUID.randomUUID();
        this.wasteCreateDate = new Date().getTime();
        this.wasteColor = Color.BLUE;//默认浅绿色
        this.wasteNotes = wasteNotes;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(WASTE_NAME,wasteName);
        jsonObject.put(WASTE_WEIGHT,wasteWeight);
        jsonObject.put(WASTE_CREATE_DATE,wasteCreateDate);
        jsonObject.put(WASTE_ID,wasteID.toString());
        jsonObject.put(WASTE_NOTES,wasteNotes);
        return jsonObject;
    }
    public ItemWaste(JSONObject jsonObject) throws JSONException {
        wasteName = jsonObject.getString(WASTE_NAME);
        wasteWeight = jsonObject.getInt(WASTE_WEIGHT);
        wasteCreateDate = jsonObject.getLong(WASTE_CREATE_DATE);
        wasteID = UUID.fromString(jsonObject.getString(WASTE_ID));
        wasteNotes = jsonObject.getString(WASTE_NOTES);
    }


    public String getWasteName() {
        return wasteName;
    }

    public void setWasteName(String wasteName) {
        this.wasteName = wasteName;
    }

    public int getWasteWeight() {
        return wasteWeight;
    }

    public void setWasteWeight(int wasteWeight) {
        this.wasteWeight = wasteWeight;
    }

    public UUID getWasteID() {
        return wasteID;
    }

    public void setWasteID(UUID wasteID) {
        this.wasteID = wasteID;
    }
    public String getWasteNotes() {
        return wasteNotes;
    }

    public void setWasteNotes(String wasteNotes) {
        this.wasteNotes = wasteNotes;
    }

    public Long getWasteCreateDate() {
        return wasteCreateDate;
    }

    public void setWasteCreateDate(Long wasteCreateDate) {
        this.wasteCreateDate = wasteCreateDate;
    }
    public int getWasteColor() {
        int[] colors = new int[]{0xff000000,0xff0000ff,0xff00ffff,0xff444444,0xff888888,0xff00ff00,0xffff00ff,0xffff0000,0xffffff00};
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(wasteCreateDate);
        setWasteColor(colors[c.get(Calendar.DAY_OF_WEEK)]);
        return wasteColor;
    }

    public void setWasteColor(int wasteColor) {
        this.wasteColor = wasteColor;
    }
}
