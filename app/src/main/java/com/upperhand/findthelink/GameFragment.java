package com.upperhand.findthelink;

import android.content.Context;
import android.content.DialogInterface;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.upperhand.findthelink.objects.RetrofitSingleton;
import com.upperhand.findthelink.objects.Score;
import com.upperhand.findthelink.objects.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class GameFragment extends Fragment {

    
    Context context;
    EditText editText;
    Button btnGiveUp;
    boolean isPremium;
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
    String TAG = "asd";
    boolean isGaveUp;
    int time;
    InterstitialAd mInterstitialAd;
    long startTime = 0;
    Handler handler = new Handler();
    Handler timerHandler = new Handler();
    Runnable timerRunnable;
    Call<Score> callPost;


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

        editText.setInputType(InputType.TYPE_NULL);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(curlevel < 201 && !isGaveUp) {
                    String text = editText.getText().toString();
                    if (text.equalsIgnoreCase(Utils.getTasks().get(curlevel).getAnswer())) {
                        Utils.makeToast("Correct!", context);
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
                isGaveUp = true;
                editText.setText(Utils.getTasks().get(curlevel).getAnswer());
                correct();
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

        loadAd();

        return view;
    }

    private void loadAd(){

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){

                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Utils.getAlertDialogue().dismiss();
                                loadAd();
                                loadTask();

                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }

    private void showAd(){

        if (mInterstitialAd != null) {
            mInterstitialAd.show(getActivity());
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }

    }

    private void resetTimer(){
        tvTime.setText(String.format("%d:%02d", 0, 0));
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 1000);
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
            tvDifficulty.setText(Utils.getTasks().get(curlevel).getDif());
            tvLevel.setText("task " + (curlevel + 1));
            editText.setText("");
            isGaveUp = false;
            btnGiveUp.setClickable(true);
            mKeyboardView.setClickable(true);
            resetTimer();
        }
    }

    private void correct(){

        btnGiveUp.setClickable(false);
        mKeyboardView.setClickable(false);
        postScore();

        timerHandler.removeCallbacks(timerRunnable);
        Utils.setSharedPref("level",curlevel + 1, context);

        Utils.buildAlertDialogue(R.layout.correct, context);
        TextView title =  Utils.getAlertDialogue().findViewById(R.id.title);
        TextView solved =  Utils.getAlertDialogue().findViewById(R.id.solved);
        TextView time_u =  Utils.getAlertDialogue().findViewById(R.id.time_a);
        TextView time_a =  Utils.getAlertDialogue().findViewById(R.id.time_u);

        Button next =  Utils.getAlertDialogue().findViewById(R.id.next);
        time_u.setVisibility(View.VISIBLE);

        time_a.setText("Average time : "+ Utils.getTasks().get(curlevel).getTime() + " sec");
        solved.setText("Solved by : "+ Utils.getTasks().get(curlevel).getSolved() + "%");

        if(!isGaveUp){
            time_u.setText("Your time : " + time + " sec");
        }else{
            title.setText(Utils.getTasks().get(curlevel).getAnswer());
            time_u.setVisibility(View.INVISIBLE);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curlevel++;
                if (!isPremium && mInterstitialAd != null && curlevel % 3 == 0 && curlevel > 2 ){
                    showAd();
                }else{
                    Utils.getAlertDialogue().dismiss();
                    loadTask();
                }
            }
        });

        Utils.getAlertDialogue().setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                curlevel++;
                if (!isPremium && mInterstitialAd != null && curlevel % 3 == 0 && curlevel > 2 ){
                    showAd();
                }else{
                    Utils.getAlertDialogue().dismiss();
                    loadTask();
                }
            }
        });

        Utils.getAlertDialogue().show();
    }

    private void postScore(){
        
        Score score = new Score(!isGaveUp,time,curlevel,getResources().getInteger(R.integer.pass));

        callPost = RetrofitSingleton.get().postData().postScore(score);

        callPost.enqueue(new Callback<Score>() {
            @Override
            public void onResponse(Call<Score> call, Response<Score> response) {

            }
            @Override
            public void onFailure(Call<Score> call, Throwable t) {
                Log.e("see",t.getMessage());
                Toast.makeText(context, "Problem connecting the server." ,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private final KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override public void onKey(int primaryCode, int[] keyCodes) {

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

    private void openKeyboard(View v) {
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setEnabled(true);
        if( v!=null)((InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void disableSoftInputFromAppearing() {
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(true);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}