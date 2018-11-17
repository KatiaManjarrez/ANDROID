package com.example.katy.aplicacin;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
//import android.support.v4.app.NotificationCompat;

import java.util.Calendar;


public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    private NotificationManager notificationManager;
    private final int NOTIFICATION_ID = 1010;
    private Cursor fila;
    private SQLiteDatabase CONTACTOS;
    private String alarma,descripcion,titulo;


    @Override
    public void onReceive(Context context, Intent intent) {

        Calendar calendario = Calendar.getInstance();
        int hora, min,dia,mes,ano;
        String cadenaF, cadenaH,fecha_sistema,hora_sistema;

        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH)+1;
        ano = calendario.get(Calendar.YEAR);
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        min = calendario.get(Calendar.MINUTE);
        fecha_sistema=mes+"-"+dia+"-"+ano+" ";
        hora_sistema=hora+":"+min;

        if(CONTACTOS!=null) {
            CONTACTOS.rawQuery("SELECT FECHA,HORA FROM ALARMA WHERE FECHA='"+fecha_sistema+"' AND HORA= '"+hora_sistema+"'", null);

                triggerNotification(context);

        }
        CONTACTOS.close();
    }

    private void triggerNotification(Context contexto) {

        NotificationCompat.Builder builer = new NotificationCompat.Builder(contexto);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com.mx"));
        PendingIntent pendingIntent = PendingIntent.getActivity(contexto,01,intent,0);
        builer.setContentIntent(pendingIntent);
        builer.setDefaults(Notification.DEFAULT_ALL);
        builer.setContentTitle("Notificación title here");
        builer.setSmallIcon(R.mipmap.ic_launcher);
        builer.setContentText("this is notification examepl");
        notificationManager = (NotificationManager) contexto.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(001,builer.build());
    }

}
