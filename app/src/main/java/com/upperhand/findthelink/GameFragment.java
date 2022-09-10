package com.upperhand.findthelink;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.upperhand.findthelink.objects.Task;
import com.upperhand.findthelink.objects.Utils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class GameFragment extends Fragment {

    
    Context context;
    EditText editText;
    Button btnGiveUp;
    boolean isPremium;
    
    boolean is_solve;
    long time_start;
    Animation fadein;
    Animation fadeout;
    TextView tvTime;
    TextView tvLevel;
    TextView tvHint_1;
    TextView tvHint_2;
    TextView tvHint_3;
    TextView tvDifficulty;
    int curlevel;
    Keyboard mKeyboard;
    KeyboardView mKeyboardView;

    boolean opening;
    int time;
    InterstitialAd mInterstitialAd;
    Handler handler = new Handler();
    long startTime = 0;
    Handler timerHandler = new Handler();
    final ArrayList<Task> tasks = new ArrayList<>();
    Runnable timerRunnable;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_game, container, false);

   
        isPremium = Utils.getSharedPref("isPremium", false, context);
        curlevel = Utils.getSharedPref("tvLevel", 0, context);

        mKeyboard = new Keyboard(context, R.xml.keyboard);
        mKeyboardView = view.findViewById(R.id.keyboardview);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        openKeyboard(mKeyboardView);
        mKeyboardView.setPreviewEnabled(false);
        disableSoftInputFromAppearing ();

        
        tvTime =  findViewById(R.id.tvTime);
        tvLevel =  findViewById(R.id.tvLevel);
        tvHint_1 =  findViewById(R.id.tvHint_1);
        tv =  findViewById(R.id.tv);
        tvHint_2_1 =  findViewById(R.id.tvHint_2_1);
        tvHint_3 =  findViewById(R.id.tvHint_3);
        tvDifficulty =  findViewById(R.id.tvDifficulty);
        editText =  findViewById(R.id.editText);
        btnGiveUp = findViewById(R.id.btnGiveUp);
        typeFace = Typeface.createFromAsset(getAssets(), "fonts/Aviano.ttf");
        fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        fadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);


        sp = getApplicationContext().getSharedPreferences("time1", 0);
        time_start = sp.getLong("time1", 0);
        if (time_start == 0) {
            time_start = TimeUnit.MILLISECONDS.toSeconds(SystemClock.elapsedRealtime());
        }



        tvLevel.setTypeface(typeFace);
        editText.setTypeface(typeFace);
        tv.setTypeface(typeFace);
        tvHint_1.setTypeface(typeFace);
        tvHint_2_1.setTypeface(typeFace);
        tvHint_3.setTypeface(typeFace);
        tvDifficulty.setTypeface(typeFace);
        tvTime.setTypeface(typeFace);
        btnGiveUp.setTypeface(typeFace);
        editText.setInputType(InputType.TYPE_NULL);

        return view;
    }

    private final KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override public void onKey(int primaryCode, int[] keyCodes) {
            if (!opening){
                if (primaryCode == -5) {
                    String str = editText.getText().toString();
                    if (str.length() > 0) {
                        str = str.substring(0, str.length() - 1);
                        editText.setText(str);
                        editText.setSelection(editText.getText().length());
                    }
                } else {
                    editText.append(Character.toString((char) primaryCode));
                }
            }
        }

        @Override public void onPress(int arg0) {
        }

        @Override public void onRelease(int primaryCode) {

        }

        @Override public void onText(CharSequence text) {
        }

        @Override public void swipeDown() {
        }

        @Override public void swipeLeft() {
        }

        @Override public void swipeRight() {
        }

        @Override public void swipeUp() {
        }
    };


    public void openKeyboard(View v) {
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setEnabled(true);
        if( v!=null)((InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void disableSoftInputFromAppearing() {
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(true);
    }
}