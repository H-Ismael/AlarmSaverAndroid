package com.devsmikou.alarm_battery_saver;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class fullscreennaughtif extends AppCompatActivity {
    ConstraintLayout lLayaout;

    ConstraintLayout myview;
    Button dismissButton;
    AnimationDrawable animationDrawable ;
    ImageView logo2;
    ImageView desac;
    Animation exit , exitph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            // For newer than Android Oreo: call setShowWhenLocked, setTurnScreenOn utility or tools
            setShowWhenLocked(true);
            setTurnScreenOn(true);


            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            keyguardManager.requestDismissKeyguard(this, null);
        } else {
            // For older versions, do it as you did before.
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }


        setContentView(R.layout.pop_up);


        lLayaout = (ConstraintLayout) findViewById(R.id.mlayout);
        animationDrawable = (AnimationDrawable) lLayaout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);
        //animationDrawable.setAlpha(10);
        animationDrawable.start();

        logo2 = (findViewById(R.id.wingy2));
        desac = (findViewById(R.id.desactivated));
        desac.setAlpha(0f);

        dismissButton = findViewById(R.id.dbutton);
/*
        boolean bStop = false;
        Intent bstopIntent = new Intent(this, AlarmReceiver.class);
        bstopIntent.putExtra("StopAlarm",bStop);
        sendBroadcast(bstopIntent);*/

        //logo entry animation


        exit = AnimationUtils.loadAnimation(this, R.anim.secondentry);
        exitph = AnimationUtils.loadAnimation(this, R.anim.desappearance);
        //logo2.setAnimation(exit);

        dismissButton.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "Dismissed, you are on you own now",Toast.LENGTH_LONG).show();
            logo2.setAnimation(exit);
            //desac.setAnimation(exitph);
            desac.animate()
                    .translationYBy(10)
                    .alpha(1f)
                    .setDuration(500);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent cIntent = new Intent(fullscreennaughtif.this , AlarmReceiver.class);
                    PendingIntent cPendingIntent = PendingIntent.getBroadcast(fullscreennaughtif.this, 0,cIntent,0);
                    ((AlarmManager)getSystemService(ALARM_SERVICE)).cancel(cPendingIntent);
                    //cPendingIntent.cancel();


                    finish();
                }
            }, 2000);
            /*
            boolean cStop = true;
            Intent cstopIntent = new Intent(this, AlarmReceiver.class);
            cstopIntent.putExtra("StopAlarm",cStop);
            sendBroadcast(cstopIntent);*/




        });

    }

    protected void onDestroy() {
        super.onDestroy();


    }

}
