package com.izp.manzifinal.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.izp.manzifinal.MainActivity;
import com.izp.manzifinal.R;
import com.izp.manzifinal.fragment.FragmentTodo;

import java.util.UUID;

/**
 * Created by izp on 2016/3/23.
 */
public class TodoNotificationService extends IntentService {
    private String todoText;
    private UUID todoUUID;

    public TodoNotificationService() {
        super("TodoNotificationService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public TodoNotificationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("vicky_","看看提醒服务运行了没");
        todoText = intent.getStringExtra(FragmentTodo.TODO_ITEM);
        todoUUID = (UUID) intent.getSerializableExtra(FragmentTodo.TODO_ID);
        Intent toActivity = new Intent(this, MainActivity.class);
        toActivity.putExtra(FragmentTodo.TODO_ID, todoUUID);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(this).setContentTitle(todoText)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(PendingIntent.getActivity(this,todoUUID.hashCode(),toActivity,PendingIntent.FLAG_UPDATE_CURRENT))
                .build();
        manager.notify(100,notification);

    }
}
