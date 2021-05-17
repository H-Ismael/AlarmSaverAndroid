package com.devsmikou.alarm_battery_saver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;

import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //logo var for animated entry
    ImageView logo, msgexp;
    //ImageView msgexp;
    Animation entry, msgexpanim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //logo entry animation

        logo = (findViewById(R.id.wingy));
        msgexp = (findViewById(R.id.noMoretxt));
        msgexp.setAlpha(0f);

        entry = AnimationUtils.loadAnimation(this, R.anim.firstentry);
        msgexpanim = AnimationUtils.loadAnimation(this, R.anim.textappearence);

        logo.setAnimation(entry);




        //gradient color animation
        ConstraintLayout constraintLayout1 = findViewById(R.id.mainLayout);
        AnimationDrawable mainAnimation = (AnimationDrawable) constraintLayout1.getBackground();
        mainAnimation.setEnterFadeDuration(500);
        mainAnimation.setExitFadeDuration(500);
        mainAnimation.start();
        //naughtif
        createNaughtificationCH();

        //button to create and send alarm intent
        Button button = findViewById(R.id.Gbutton);
        button.setOnClickListener(view -> {
            Toast.makeText(this,"You are all set",Toast.LENGTH_LONG).show();
            //msgexp.setAnimation(msgexpanim);
            msgexp.animate()
                    .alpha(1f)
                    .setDuration(500);

            Intent mIntent = new Intent(MainActivity.this, AlarmReceiver.class);
            PendingIntent mPendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,mIntent,0);

            AlarmManager mAalarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            long timeAtButtonClick = System.currentTimeMillis();
            long fivemin = 1000 * 60 * 10;
            mAalarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeAtButtonClick + fivemin, fivemin, mPendingIntent);

        });

        //cancel button
        Button cbutton = findViewById(R.id.Cbutton);
        cbutton.setOnClickListener(view -> {
         finish();
        });
    }

    //naughtif channel creation
    private void createNaughtificationCH(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1){
            CharSequence chName = "NaughtyChannel";
            String description = "Channel for AlarmReceiver";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("naughtifyID", chName,importance);
            channel.setDescription(description);

            NotificationManager naughtificationManager = getSystemService(NotificationManager.class);

            /*
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + getPackageName() + "/raw/raphamaelmix.mp3");
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            channel.setSound(alarmSound, attributes);

             */

            naughtificationManager.createNotificationChannel(channel);

        }

    }

    //vibrationFunction

    public void viber(Context context){

        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        long[] pattern = {1500, 1000};
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            VibrationEffect vibe = VibrationEffect.createWaveform(pattern, 4);
            vibrator.vibrate(vibe);
        } else {
            vibrator.vibrate(pattern, 4);
        }
    }
}

