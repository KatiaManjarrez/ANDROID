package com.example.katy.aplicacin;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {


        NotificationManager notificationManager;

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent service1 = new Intent(context, MyAlarmReceiver.class);
            context.startService(service1);
        }
    }

