package com.devsmikou.alarm_battery_saver;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import java.text.DateFormat;
import java.util.Date;



public class AlarmReceiver extends BroadcastReceiver {
    MediaPlayer mp;

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





    public String isAlarmSafe(Context context , Intent intent){
        boolean cancelAlarm = false;

        String safy = "battery is safe";
        String notsafy = "battery not safe";

        boolean dStop = intent.getExtras().getBoolean("StopAlarm");

        if ((getNAlarm(context) < 160) && (getBatteryPercentage(context) < 22) ){

            //if(!mp.isPlaying())
            //    alertPopUp();
            naughtyF(context);
            Toast.makeText(context.getApplicationContext(),"Battery daying , you may miss you next AlarmClock",Toast.LENGTH_LONG).show();
            long[] timing = {1000,1000};
            int[]  amp = {50,150};
            Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            VibrationEffect ve = VibrationEffect.createOneShot(2000,VibrationEffect.DEFAULT_AMPLITUDE);

            v.vibrate(ve, audioAttributes);

            v.vibrate(ve, audioAttributes);

            mp = MediaPlayer.create(context,R.raw.ralph);
            mp.setScreenOnWhilePlaying(true);
            //mp.setLooping(true);
            mp.setVolume(0.8f,0.8f);

            while(mp.isPlaying() != true){

                mp.start();
            }

            v.vibrate(ve, audioAttributes);

/*
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, 5),
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setUsage(AudioAttributes.USAGE_ALARM)
                            .build());
            */



            //mp.start();

            return notsafy ;
        } else {
            Toast.makeText(context.getApplicationContext(),"your alarm clock are safe ...for now haha",Toast.LENGTH_LONG).show();
            return safy ;
        }
    }

    public void naughtyF(Context context){

        Intent fullscreeenintent = new Intent(context , fullscreennaughtif.class);
        //caaarefull flag added 1/7/21
        fullscreeenintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        PendingIntent fullscreenpendingintent = PendingIntent.getActivity(context,0,fullscreeenintent,PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getPackageName() + "/raw/ralph.mp3");
        long[] pattern = {0,1000,1500};
        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context, "naughtifyID")
                .setSmallIcon(R.drawable.notiff)
                .setContentTitle("Your Alarm Saver")
                .setContentText("Low battery ! you may miss your scheduled alarm ")
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setFullScreenIntent(fullscreenpendingintent,true)
                .setVibrate(pattern)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);


        NotificationManagerCompat mNaughtyManager = NotificationManagerCompat.from(context);
        mNaughtyManager.notify(100,mbuilder.build());
    }

    public void viber(Context context){

        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        long[] pattern = {1500, 800};
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            VibrationEffect vibe = VibrationEffect.createWaveform(pattern, 4);

            vibrator.vibrate(vibe);
        } else {
            vibrator.vibrate(pattern, 4);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Intent newintent = new Intent(context, ImageDecoder.ImageInfo.class);

        isAlarmSafe(context,intent);
        //boolean sStop = intent.getExtras().getBoolean("StopAlarm");
        /*
        if(sStop){
            Toast.makeText(context, "test send po", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "else succeeded",Toast.LENGTH_LONG).show();
        }*/




    }
}
