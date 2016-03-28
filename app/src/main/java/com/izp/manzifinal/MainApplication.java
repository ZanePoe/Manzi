package com.izp.manzifinal;

import android.content.Context;

import com.izp.manzifinal.broadcaster.DaemonReceiver;
import com.izp.manzifinal.broadcaster.DaemonReceiver0;
import com.izp.manzifinal.service.DaemonService;
import com.izp.manzifinal.service.TodoNotificationService;
import com.marswin89.marsdaemon.DaemonApplication;
import com.marswin89.marsdaemon.DaemonConfigurations;

/**
 * Created by izp on 2016/3/25.
 */
public class MainApplication extends DaemonApplication {
    @Override
    protected DaemonConfigurations getDaemonConfigurations() {
        DaemonConfigurations.DaemonConfiguration daemonConfiguration = new DaemonConfigurations.DaemonConfiguration(
                "com.izp.manzifinal.resident_process",
                TodoNotificationService.class.getCanonicalName(),
                DaemonReceiver0.class.getCanonicalName()
        );
        DaemonConfigurations.DaemonConfiguration configuration = new DaemonConfigurations.DaemonConfiguration(
                "com.izp.manzifinal.resident_process",
                DaemonService.class.getCanonicalName(),
                DaemonReceiver.class.getCanonicalName()
        );
        DaemonConfigurations.DaemonListener listener = new MyDaemonListener();
        return new DaemonConfigurations(daemonConfiguration, configuration, listener);
    }

    class MyDaemonListener implements DaemonConfigurations.DaemonListener{
        @Override
        public void onPersistentStart(Context context) {
        }

        @Override
        public void onDaemonAssistantStart(Context context) {
        }

        @Override
        public void onWatchDaemonDaed() {
        }
    }
}
