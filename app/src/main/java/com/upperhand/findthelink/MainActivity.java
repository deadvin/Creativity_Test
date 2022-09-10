package com.upperhand.findthelink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.upperhand.findthelink.objects.Utils;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RelativeLayout btnStart;
    ImageView btnMusic;
    ImageView btnInfo;
    ImageView btnTests;
    TextView tvTitle;
    TextView tvText;
    TextView tvStart;
    TextView tvTasksSolved;
    RullesFragment rullesFragment;
    TestsFragment testsFragment;
    GameFragment gameFragment;
    FragmentTransaction transaction;
    FragmentManager fragmentManager;
    Context context;
    MediaPlayer mediaPlayer;
    int appId;
    boolean isFragmentTop;
    boolean premium;
    int level;
    boolean musicon = true;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_main);

        context = this;
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
        tvTitle =findViewById(R.id.tvTitle);
        tvText = findViewById(R.id.tvText);
        tvStart = findViewById(R.id.tvStart);
        tvTasksSolved = findViewById(R.id.tvTasksSolved);
        btnStart = findViewById(R.id.btnStart);
        btnMusic = findViewById(R.id.btnMusic);
        btnTests = findViewById(R.id.btnTests);
        btnInfo = findViewById(R.id.btnInfo);

        rullesFragment = new RullesFragment();
        testsFragment = new TestsFragment();
        gameFragment = new GameFragment();

        fragmentManager = this.getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.fragment_container, rullesFragment);
        transaction.add(R.id.fragment_container, testsFragment);
        transaction.add(R.id.fragment_container, gameFragment);

        transaction.hide(rullesFragment);
        transaction.hide(testsFragment);
        transaction.hide(gameFragment);
        transaction.commit();

        level = Utils.getSharedPref("level" , 0, context);
        premium = Utils.getSharedPref("premium", false, context);
        musicon =  Utils.getSharedPref("music", true, context);

        tvTasksSolved.setText(level+"/200");

        appId = Utils.getSharedPref("id", 0, context);
        if(appId == 0){
            Random r = new Random();
            int ran = r.nextInt(10000000) + 1;
            Utils.setSharedPref("id",ran, context);
            appId = ran;
        }
        Utils.loadTasks();

        if(musicon) {
            btnMusic.setImageResource(R.drawable.music_btn_on);
        }else {
            btnMusic.setImageResource(R.drawable.music_btn_off);
        }


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                transaction.show(gameFragment);
                transaction.hide(rullesFragment);
                transaction.hide(testsFragment);
                transaction.commit();
                isFragmentTop = true;
            }
        });

        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                musicon = !musicon;
                Utils.setSharedPref("music", musicon, context);
                if(musicon) {
                    mediaPlayer.start();

                    btnMusic.setImageResource(R.drawable.music_btn_on);
                }else {
                    mediaPlayer.pause();
                    btnMusic.setImageResource(R.drawable.music_btn_off);
                }
            }
        });

        btnTests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                transaction.hide(gameFragment);
                transaction.hide(rullesFragment);
                transaction.show(testsFragment);
                transaction.commit();
                isFragmentTop = true;
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                transaction.hide(gameFragment);
                transaction.show(rullesFragment);
                transaction.hide(testsFragment);
                transaction.commit();
                isFragmentTop = true;
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.v_dark));
        }
    }


    @Override
    public void onBackPressed() {

        if(isFragmentTop) {
            isFragmentTop = false;
            transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
            transaction.hide(gameFragment);
            transaction.hide(rullesFragment);
            transaction.hide(testsFragment);
            transaction.commit();
        }else {
            moveTaskToBack(true);
        }
    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        mediaPlayer.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if(musicon) {
            mediaPlayer.start();
        }
        super.onResume();
    }
}
