package com.upperhand.findthelink;

import android.app.Dialog;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.upperhand.findthelink.objects.Task;
import com.upperhand.findthelink.objects.Utils;
import java.util.ArrayList;
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
        curlevel = Utils.getSharedPref("level", 0, context);

        mKeyboard = new Keyboard(context, R.xml.keyboard);
        mKeyboardView = view.findViewById(R.id.keyboardview);
        tvTime =  view.findViewById(R.id.time);
        tvLevel =   view.findViewById(R.id.level);
        tvHint_1 =   view.findViewById(R.id.hint1);
        tvHint_2 =  view.findViewById(R.id.hint2);
        tvHint_3 =  view.findViewById(R.id.hint3);
        tvDifficulty =  view.findViewById(R.id.dif);
        editText =  view.findViewById(R.id.editText);
        btnGiveUp = view.findViewById(R.id.giveUp);

        fadein = AnimationUtils.loadAnimation(context, R.anim.fadein);
        fadeout = AnimationUtils.loadAnimation(context, R.anim.fadeout);

        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        openKeyboard(mKeyboardView);
        mKeyboardView.setPreviewEnabled(false);
        disableSoftInputFromAppearing ();


//        sp = getApplicationContext().getSharedPreferences("time1", 0);
//        time_start = sp.getLong("time1", 0);
//        if (time_start == 0) {
//            time_start = TimeUnit.MILLISECONDS.toSeconds(SystemClock.elapsedRealtime());
//        }

        editText.setInputType(InputType.TYPE_NULL);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(curlevel < 201) {
                    String text = editText.getText().toString();
                    if (text.equalsIgnoreCase(tasks.get(curlevel).getAnswer())) {
                        Utils.makeToast("Correct!", context);
                        timerHandler.removeCallbacks(timerRunnable);
                        correct();

                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnGiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!opening) {
                    opening = true;
                    is_solve = false;
                    editText.setText(tasks.get(curlevel).getAnswer());
                }
            }
        });

        timerRunnable = new Runnable() {

            @Override
            public void run() {
                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                time = (int) (millis / 1000);
                tvTime.setText(String.format("%d:%02d", minutes, seconds));
                timerHandler.postDelayed(this, 500);
            }
        };

        loadTask();

        return view;
    }



    public void loadTask (){

        if(curlevel==200) {

//            timer.setVisibility(View.INVISIBLE);
//            level.setVisibility(View.INVISIBLE);
//            Dialog nolevels = new Dialog(Game.this);
//            nolevels.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//            nolevels.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            nolevels.setContentView(R.layout.nolevels);
//            nolevels.setCancelable(true);
//            Window window2 = nolevels.getWindow();
//            window2.setGravity(Gravity.CENTER);
//            Button cancel2 = (Button) nolevels.findViewById(R.id.cancel);
//            TextView title2 = (TextView) nolevels.findViewById(R.id.title);
//            cancel2.setTypeface(typeFace);
//            title2.setTypeface(typeFace);
//            cancel2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent myIntent = new Intent(Game.this, Start.class);
//                    Game.this.startActivity(myIntent);
//                }
//            });
//            nolevels.show();
        }else{
            tvHint_1.setText(Utils.getTasks().get(curlevel).getHint1());
            tvHint_2.setText(Utils.getTasks().get(curlevel).getHint2());
            tvHint_3.setText(Utils.getTasks().get(curlevel).getHint3());

//            tvHint_1.setGravity(Gravity.CENTER);
//            tvHint_2.setGravity(Gravity.CENTER);
//            tvHint_2.setGravity(Gravity.CENTER);
            tvDifficulty.setText(Utils.getTasks().get(curlevel).getDif());
            tvLevel.setText("task " + (curlevel + 1));
//            is_solve = true;
//            opening = false;
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
            editText.setText("");
        }
    }

    public void correct(){

//        MobileAds.initialize(context, "ca-app-pub-1582921325835661~9439420037");

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                final Dialog customDialogEnd =  new Dialog(context);
                customDialogEnd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                customDialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialogEnd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                customDialogEnd.setContentView(R.layout.correct);
                customDialogEnd.setCancelable(false);
                Window window = customDialogEnd.getWindow();
                window.setGravity(Gravity.CENTER);

                TextView title =  customDialogEnd.findViewById(R.id.title);
                TextView solved =  customDialogEnd.findViewById(R.id.solved);
                TextView time_u =  customDialogEnd.findViewById(R.id.time_a);
                TextView time_a =  customDialogEnd.findViewById(R.id.time_u);
                ImageView arrow =  customDialogEnd.findViewById(R.id.imageView4);
                ImageView box =  customDialogEnd.findViewById(R.id.imageView3);

                Button next =  customDialogEnd.findViewById(R.id.next);
                time_u.setVisibility(View.VISIBLE);
                arrow.setVisibility(View.VISIBLE);



                time_a.setText("Average time : "+ tasks.get(curlevel).getTime() + " sec");
                solved.setText("Solved by : "+ tasks.get(curlevel).getSolved() + "%");

                if(is_solve){
                    box.setImageResource(R.drawable.right);
                    time_u.setText("Your time : " + time + " sec");
                    if(time < tasks.get(curlevel).getTime()){
                        arrow.setImageResource(R.drawable.green);
                    }else if (time > tasks.get(curlevel).getTime()){
                        arrow.setImageResource(R.drawable.red);
                    }else{
                        arrow.setVisibility(View.GONE);
                    }
                }else{
                    title.setText(tasks.get(curlevel).getAnswer());
                    box.setImageResource(R.drawable.wrong);
                    time_u.setVisibility(View.GONE);
                    arrow.setVisibility(View.GONE);
                }

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        curlevel++;

                        if (!isPremium && mInterstitialAd.isLoaded() && curlevel % 3 == 0 && curlevel > 2 ){
                            mInterstitialAd.setAdListener(new AdListener() {
                                @Override
                                public void onAdClosed() {
                                    super.onAdClosed();
                                    customDialogEnd.dismiss();
                                    loadTask();
                                }
                            });
                            mInterstitialAd.show();
                        }else{
                            customDialogEnd.dismiss();
                            loadTask();
                        }
                    }
                });

                customDialogEnd.show();

                if (!premium) {
                    mInterstitialAd = new InterstitialAd(context);
                    mInterstitialAd.setAdUnitId("ca-app-pub-1582921325835661/1916153231");
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            //     Toast.makeText(Game.this,"ad loaded", Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onAdClosed() {
                            requestNewInterstitial();
                        }
                    });
                    requestNewInterstitial();
                }

            }

        }, 3000);

        Utils.setSharedPref("level",curlevel + 1, context);
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