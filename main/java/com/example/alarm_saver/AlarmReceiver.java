package com.example.alarm_saver;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.DateFormat;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {

    public int getNAlarm(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        long currentTime = System.currentTimeMillis();
        AlarmManager.AlarmClockInfo info = alarmManager.getNextAlarmClock();
        int diffMout = 0;
        if (info != null) {
            long diff = info.getTriggerTime() - currentTime;
            long diffMinutes = (diff + 30000) / 60000;
            diffMout = ((int) diffMinutes);
            long diffDays = diffMinutes / 60 / 24;
            long diffHours = (diffMinutes / 60) % 24;
            diffMinutes = diffMinutes % 60;

            Date alarmDate = new Date(info.getTriggerTime());
            String alarmStr = DateFormat.getTimeInstance(DateFormat.SHORT).format(alarmDate);

            /*
            if (diffDays > 0)
                alarmStr = new SimpleDateFormat("EEE").format(alarmDate) + " " + alarmStr;

            String diffStr = getResources().getQuantityString(R.plurals.x_minutes, (int) diffMinutes, (int) diffMinutes);

            if (diffHours > 0 || diffDays > 0) {
                diffStr = getResources().getQuantityString(R.plurals.x_hours,
                        (int) diffHours, (int) diffHours) + " " + diffStr;
                if (diffDays > 0)
                    diffStr = getResources().getQuantityString(R.plurals.x_days,
                            (int) diffDays, (int) diffDays) + " " + diffStr;
            }
            //Toast.makeText(this, getString(R.string.alarm_time, alarmStr, diffStr),
            //Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(this, R.string.no_alarm, Toast.LENGTH_SHORT).show();
        }

             */

        }
        String dfMS = Integer.toString(diffMout);;
        return diffMout;}


    public int getBatteryPercentage(Context context) {
        int lvl = 0;
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        int currentLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);


        float level = 0;
        if (currentLevel >= 0 && scale > 0) {
            level = (currentLevel * 100) / scale;
            lvl = ((int) level);
        }
        //batteryPercent.setText("Battery Level Remaining: " + level + "%");
        //jjjjjjjjjj

        return lvl;}



    public String isAlarmSafe(Context context){
        String safy = "safee";
        String notsafy = "notsafe";
        if ((getNAlarm(context) < 20) && (getBatteryPercentage(context) < 100)){

            //if(!mp.isPlaying())
            //    alertPopUp();
            Toast.makeText(context.getApplicationContext(),"Battery daying , you may miss you next AlarmClock",Toast.LENGTH_LONG).show();
            //mp.start();


            return notsafy ;
        } else {
            Toast.makeText(context.getApplicationContext(),"your alarm clock are safe ...for now haha",Toast.LENGTH_LONG).show();
            return safy ;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        isAlarmSafe(context);

        
    }
}
