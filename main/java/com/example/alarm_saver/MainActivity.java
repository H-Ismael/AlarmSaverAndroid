package com.example.alarm_saver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNaughtification();
        Button button = findViewById(R.id.Gbutton);
        button.setOnClickListener(view -> {
            Toast.makeText(this,"routineSet",Toast.LENGTH_LONG).show();

            Intent mIntent = new Intent(MainActivity.this, AlarmReceiver.class);
            PendingIntent mPendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,mIntent,0);

            AlarmManager mAalarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            long timeAtButtonClick = System.currentTimeMillis();
            long fivemin = 1000 * 60  ;
            mAalarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeAtButtonClick + fivemin, fivemin, mPendingIntent);
        });
    }

    private void createNaughtification(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1){
            CharSequence chName = "NaughtyChannel";
            String description = "Channel for AlarmReceiver";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("naughtifyID", chName,importance);
            channel.setDescription(description);

            NotificationManager naughtificationManager = getSystemService(NotificationManager.class);
            naughtificationManager.createNotificationChannel(channel);

        }

    }
}