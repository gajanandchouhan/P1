package com.superlifesecretcode.app.ui.player;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.superlifesecretcode.app.R;

import java.io.IOException;

public class PLayerPopupActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    TextView textViewTitle;
    TextView textViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.alert_bg);
        setContentView(R.layout.activity_player_popup);
        textViewMessage = findViewById(R.id.textView_message);
        textViewTitle = findViewById(R.id.textView_title);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle!=null) {
            textViewMessage.setText(bundle.getString("body"));
            textViewTitle.setText(bundle.getString("title"));
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

        findViewById(R.id.button_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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