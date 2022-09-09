package com.upperhand.findthelink;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class GameFragment extends Fragment {

    EditText et;
    Button give_up;
    boolean premium;
    boolean is_solve;
    Typeface typeFace;
    long time_start;
    Animation fadein;
    Animation fadeout;
    TextView timer;
    TextView level;
    TextView tv1;
    TextView tv;
    TextView tv2;
    TextView tv3;
    TextView dif;
    int curlevel;
    boolean opening;
    int time;
    InterstitialAd mInterstitialAd;
    SharedPreferences sp;
    SharedPreferences.Editor sp_editor;
    Handler handler = new Handler();
    boolean continueMusic = true;
    boolean musicon;
    Keyboard mKeyboard;
    KeyboardView mKeyboardView;
    long startTime = 0;
    Handler timerHandler = new Handler();
    final ArrayList<Task> tasks = new ArrayList<>();
    Runnable timerRunnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tests, container, false);


    }
}