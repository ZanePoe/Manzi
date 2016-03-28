package com.izp.manzifinal.util;

import android.content.Context;
import android.util.Log;

import com.izp.manzifinal.model.ItemTodo;
import com.izp.manzifinal.model.ItemWaste;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by izp on 2016/2/24.
 */
public class SaveLoadData {
    public Context context;
    public String fileTodo;
    public String fileWaste;


    public SaveLoadData(String fileTodo, Context context) {
        this.fileTodo = fileTodo;
        this.context = context;
    }

    public SaveLoadData(Context context, String fileWaste) {
        this.context = context;
        this.fileWaste = fileWaste;
    }

    public static JSONArray todoToJSONs(ArrayList<ItemTodo> itemTodos) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (ItemTodo itemTodo:itemTodos){
            JSONObject jsonObject = itemTodo.toJSON();
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
    public static JSONArray waseTotJSONs(ArrayList<ItemWaste> itemWastes) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (ItemWaste itemWaste:itemWastes){
            JSONObject jsonObject = itemWaste.toJSON();
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
    public void saveTodo(ArrayList<ItemTodo> itemTodos) throws IOException, JSONException {
        FileOutputStream fileOutputStream;
        OutputStreamWriter outputStreamWriter;
        fileOutputStream = context.openFileOutput(fileTodo,Context.MODE_PRIVATE);
        outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        if (itemTodos.size()>0){
            Log.d("vicky_SaveLoad","保存成文件的时候数据的格式"+String.valueOf(itemTodos.get(0).getToDoCreateDate()));
        }
        outputStreamWriter.write(todoToJSONs(itemTodos).toString());
        outputStreamWriter.close();
        fileOutputStream.close();
    }
    public void saveWaste(ArrayList<ItemWaste> itemWastes) throws IOException, JSONException {
        FileOutputStream fileOutputStream;
        OutputStreamWriter outputStreamWriter;
        fileOutputStream = context.openFileOutput(fileWaste,Context.MODE_PRIVATE);
        outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        outputStreamWriter.write(waseTotJSONs(itemWastes).toString());
        outputStreamWriter.close();
        fileOutputStream.close();
    }
    /*从文件获取json，并转换为数据Object并添加到list中返回给调用者*/
    public ArrayList<ItemTodo> loadTodo() throws IOException {
        ArrayList<ItemTodo> itemTodos = new ArrayList<>();
        BufferedReader bufferedReader = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(fileTodo);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }//已经把直接转为一个reader类型了
            JSONArray jsonArray = (JSONArray) new JSONTokener(stringBuilder.toString()).nextValue();
            for (int i = 0;i<jsonArray.length();i++){
                ItemTodo itemTodo = new ItemTodo(jsonArray.getJSONObject(i));
                itemTodos.add(itemTodo);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if (bufferedReader!=null){
                bufferedReader.close();
            }
            if (fileInputStream!=null){
                fileInputStream.close();
            }
        }
        return itemTodos;
    }
    public ArrayList<ItemWaste> loadWaste() throws IOException {
        ArrayList<ItemWaste> itemWastes = new ArrayList<>();
        BufferedReader bufferedReader = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(fileWaste);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
//            for ((line = bufferedReader.readLine()) != null ){
//
//            }
            while ((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            JSONArray jsonArray = (JSONArray) new JSONTokener(stringBuilder.toString()).nextValue();
            for (int i = 0;i<jsonArray.length();i++){
                ItemWaste itemWaste = new ItemWaste(jsonArray.getJSONObject(i));
                itemWastes.add(itemWaste);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if (bufferedReader !=null){
                bufferedReader.close();
            }
            if (fileInputStream != null){
                fileInputStream.close();
            }
        }
        return itemWastes;
    }
}
