package com.superlifesecretcode.app.ui.player;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.superlifesecretcode.app.R;
import com.superlifesecretcode.app.data.model.language.LanguageResponseData;
import com.superlifesecretcode.app.data.persistance.SuperLifeSecretPreferences;

import java.io.IOException;

public class PLayerPopupActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    TextView textViewTitle;
    TextView textViewMessage;
    private KeyguardManager myKM;
    LinearLayout linearLayout;
//    private PowerManager powerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.alert_bg);
        setContentView(R.layout.activity_player_popup);
        textViewMessage = findViewById(R.id.textView_message);
        textViewTitle = findViewById(R.id.textView_title);
        linearLayout = findViewById(R.id.linearlayout);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        LanguageResponseData responseData = SuperLifeSecretPreferences.getInstance().getConversionData();
        if (bundle != null) {
            textViewMessage.setText(String.format("%s %s", responseData.getNew_event_near(), bundle.getString("body")));
            textViewTitle.setText(responseData.getRichest_life_reminder());
        }
        setFinishOnTouchOutside(false);
        Uri myUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), myUri);
            mediaPlayer.setLooping(true);
            mediaPlayer.setScreenOnWhilePlaying(true);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button buttonDismiss = findViewById(R.id.button_dismiss);
        buttonDismiss.setText(SuperLifeSecretPreferences.getInstance().getConversionData().getDismiss());
        buttonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             onBackPressed();
            }
        });
        myKM = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        boolean isPhoneLocked = myKM.inKeyguardRestrictedInputMode();
//        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

    }

    private boolean isScreenOn() {
//        return (Build.VERSION.SDK_INT < 20 ? powerManager.isScreenOn() : powerManager.isInteractive());
        boolean isPhoneLocked = myKM.inKeyguardRestrictedInputMode();
        return !isPhoneLocked;
    }

    @Override
    protected void onPause() {
        if (isScreenOn()) {
            finish();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null)
            mediaPlayer.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}
