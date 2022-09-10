//package com.upperhand.findthelink;
//
//import android.app.Dialog;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Typeface;
//import android.inputmethodservice.Keyboard;
//import android.inputmethodservice.KeyboardView;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.SystemClock;
//import android.text.Editable;
//import android.text.InputType;
//import android.text.TextWatcher;
//import android.view.Gravity;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.interstitial.InterstitialAd;
//
//import java.util.ArrayList;
//import java.util.concurrent.TimeUnit;
//
//public class Game extends AppCompatActivity {
//
//    //region Variables
//
//    EditText et;
//    Button give_up;
//    boolean premium;
//    boolean is_solve;
//    Typeface typeFace;
//    long time_start;
//    Animation fadein;
//    Animation fadeout;
//    TextView timer;
//    TextView level;
//    TextView tv1;
//    TextView tv;
//    TextView tv2;
//    TextView tv3;
//    TextView dif;
//    int curlevel;
//    boolean opening;
//    int time;
//    InterstitialAd mInterstitialAd;
//    SharedPreferences sp;
//    SharedPreferences.Editor sp_editor;
//    Handler handler = new Handler();
//    boolean continueMusic = true;
//    boolean musicon;
//    Keyboard mKeyboard;
//    KeyboardView mKeyboardView;
//    long startTime = 0;
//    Handler timerHandler = new Handler();
//    final ArrayList<Task> tasks = new ArrayList<>();
//    Runnable timerRunnable;
//
//    //endregion
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//        this.setContentView(R.layout.activity_game);
//
//        //region Declaration
//
//        final SharedPreferences prempref = getApplicationContext().getSharedPreferences("premium", 0);
//        premium = prempref.getBoolean("premium", false);
//        timer =  findViewById(R.id.timer);
//        level =  findViewById(R.id.level);
//        tv1 =  findViewById(R.id.tv1);
//        tv =  findViewById(R.id.tv);
//        tv2 =  findViewById(R.id.tv2);
//        tv3 =  findViewById(R.id.tv3);
//        dif =  findViewById(R.id.dif);
//        et =  findViewById(R.id.editText);
//        give_up = findViewById(R.id.give_up);
//        typeFace = Typeface.createFromAsset(getAssets(), "fonts/Aviano.ttf");
//        fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
//        fadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
//        sp = getApplicationContext().getSharedPreferences("music", 0);
//        musicon = sp.getBoolean("music", true);
//        sp = getApplicationContext().getSharedPreferences("level", 0);
//        curlevel = sp.getInt("level", 0);
//        sp = getApplicationContext().getSharedPreferences("time1", 0);
//        time_start = sp.getLong("time1", 0);
//        if (time_start == 0) {
//            time_start = TimeUnit.MILLISECONDS.toSeconds(SystemClock.elapsedRealtime());
//        }
//        mKeyboard = new Keyboard(this, R.xml.keyboard);
//        mKeyboardView = findViewById(R.id.keyboardview);
//        mKeyboardView.setKeyboard( mKeyboard );
//        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
//        openKeyboard(mKeyboardView);
//        mKeyboardView.setPreviewEnabled(false);
//        disableSoftInputFromAppearing ();
//        level.setTypeface(typeFace);
//        et.setTypeface(typeFace);
//        tv.setTypeface(typeFace);
//        tv1.setTypeface(typeFace);
//        tv2.setTypeface(typeFace);
//        tv3.setTypeface(typeFace);
//        dif.setTypeface(typeFace);
//        timer.setTypeface(typeFace);
//        give_up.setTypeface(typeFace);
//        et.setInputType(InputType.TYPE_NULL);
//
//        //endregion
//
//        tasks();
//
//        //   TIMER
//
//          timerRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                long millis = System.currentTimeMillis() - startTime;
//                int seconds = (int) (millis / 1000);
//                int minutes = seconds / 60;
//                seconds = seconds % 60;
//                time = (int) (millis / 1000);
//                timer.setText(String.format("%d:%02d", minutes, seconds));
//                timerHandler.postDelayed(this, 500);
//            }
//        };
//
//        //========================================== TEXT CHANGE LISTENER ==========================================//
//
//        et.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                if(curlevel < 201) {
//                    String text = et.getText().toString();
//                    if (text.equalsIgnoreCase(tasks.get(curlevel).getAnswer())) {
//                        if (is_solve) {
//                            Toast.makeText(Game.this,
//                                    "Correct!", Toast.LENGTH_SHORT).show();
//                        }
//                        timerHandler.removeCallbacks(timerRunnable);
//                        correct();
//                    }
//                }
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//
//        give_up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(!opening) {
//                    opening = true;
//                    is_solve = false;
//                    et.setText(tasks.get(curlevel).getAnswer());
//                }
//            }
//        });
//
//        inflate();
//    }
//
//    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
//        @Override
//        public void onKey(int primaryCode, int[] keyCodes) {
//            if (!opening){
//                if (primaryCode == -5) {
//                    String str = et.getText().toString();
//                    if (str.length() > 0) {
//                        str = str.substring(0, str.length() - 1);
//                        et.setText(str);
//                        et.setSelection(et.getText().length());
//                    }
//                } else {
//                    et.append(Character.toString((char) primaryCode));
//                }
//           }
//        }
//
//        @Override
//        public void onPress(int arg0) {
//        }
//
//        @Override
//        public void onRelease(int primaryCode) {
//
//        }
//
//        @Override
//        public void onText(CharSequence text) {
//        }
//
//        @Override
//        public void swipeDown() {
//        }
//
//        @Override
//        public void swipeLeft() {
//        }
//
//        @Override
//        public void swipeRight() {
//        }
//
//        @Override
//        public void swipeUp() {
//        }
//    };
//
//    public void inflate (){
//
//        if(curlevel==200) {
//
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
//                 Intent myIntent = new Intent(Game.this, Start.class);
//                 Game.this.startActivity(myIntent);
//                }
//            });
//            nolevels.show();
//        }else{
//            tv1.setText(tasks.get(curlevel).getHint1());
//            tv2.setText(tasks.get(curlevel).getHint2());
//            tv3.setText(tasks.get(curlevel).getHint3());
//            tv1.setGravity(Gravity.CENTER);
//            tv2.setGravity(Gravity.CENTER);
//            tv3.setGravity(Gravity.CENTER);
//            dif.setText(tasks.get(curlevel).getDif());
//            level.setText("task " + (curlevel + 1));
//            is_solve = true;
//            opening = false;
//            startTime = System.currentTimeMillis();
//            timerHandler.postDelayed(timerRunnable, 0);
//            et.setText("");
//        }
//    }
//
//    public void correct(){
//
//        MobileAds.initialize(this, "ca-app-pub-1582921325835661~9439420037");
//        handler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                final Dialog customDialogEnd =  new Dialog(Game.this);
//                customDialogEnd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                customDialogEnd.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                customDialogEnd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
//                customDialogEnd.setContentView(R.layout.correct);
//                customDialogEnd.setCancelable(false);
//                Window window = customDialogEnd.getWindow();
//                window.setGravity(Gravity.CENTER);
//
//                TextView title =  customDialogEnd.findViewById(R.id.title);
//                TextView solved =  customDialogEnd.findViewById(R.id.solved);
//                TextView time_u =  customDialogEnd.findViewById(R.id.time_a);
//                TextView time_a =  customDialogEnd.findViewById(R.id.time_u);
//                ImageView arrow =  customDialogEnd.findViewById(R.id.imageView4);
//                ImageView box =  customDialogEnd.findViewById(R.id.imageView3);
//
//                Button next =  customDialogEnd.findViewById(R.id.next);
//                time_u.setVisibility(View.VISIBLE);
//                arrow.setVisibility(View.VISIBLE);
//
//                next.setTypeface(typeFace);
//                title.setTypeface(typeFace);
//                solved.setTypeface(typeFace);
//                time_u.setTypeface(typeFace);
//                time_a.setTypeface(typeFace);
//
//                time_a.setText("Average time : "+ tasks.get(curlevel).getTime() + " sec");
//                solved.setText("Solved by : "+ tasks.get(curlevel).getSolved() + "%");
//
//                if(is_solve){
//                    box.setImageResource(R.drawable.right);
//                    time_u.setText("Your time : " + time + " sec");
//                    if(time < tasks.get(curlevel).getTime()){
//                        arrow.setImageResource(R.drawable.green);
//                    }else if (time > tasks.get(curlevel).getTime()){
//                        arrow.setImageResource(R.drawable.red);
//                    }else{
//                        arrow.setVisibility(View.GONE);
//                    }
//                }else{
//                    title.setText(tasks.get(curlevel).getAnswer());
//                    box.setImageResource(R.drawable.wrong);
//                    time_u.setVisibility(View.GONE);
//                    arrow.setVisibility(View.GONE);
//                }
//
//                next.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        curlevel++;
//
//                        if (!premium && mInterstitialAd.isLoaded() && curlevel % 3 == 0 && curlevel > 2 ){
//                            mInterstitialAd.setAdListener(new AdListener() {
//                                @Override
//                                public void onAdClosed() {
//                                    super.onAdClosed();
//                                    customDialogEnd.dismiss();
//                                    inflate();
//                                }
//                            });
//                            mInterstitialAd.show();
//                        }else{
//                            customDialogEnd.dismiss();
//                            inflate();
//                        }
//                    }
//                });
//
//                customDialogEnd.show();
//
//                if (!premium) {
//                    mInterstitialAd = new InterstitialAd(Game.this);
//                    mInterstitialAd.setAdUnitId("ca-app-pub-1582921325835661/1916153231");
//                    mInterstitialAd.setAdListener(new AdListener() {
//
//
//                        @Override
//                        public void onAdLoaded() {
//                            //     Toast.makeText(Game.this,"ad loaded", Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onAdClosed() {
//                            requestNewInterstitial();
//                        }
//                    });
//
//                    requestNewInterstitial();
//                }
//
//            }
//
//        }, 3000);
//
//        sp = getApplicationContext().getSharedPreferences("level", 0);
//        sp_editor = sp.edit();
//        sp_editor.putInt("level",curlevel + 1);
//        sp_editor.apply();
//
//
//    }
//
//    public void tasks() {
//
//        tasks.add(new Task("cane", "daddy", "plum", "sugar", "Very Easy", 6, 97));
//        tasks.add(new Task("dew", "comb", "bee", "honey", "Very Easy", 6, 97));
//        tasks.add(new Task("night", "wrist", "stop", "watch", "Very Easy", 6, 96));
//        tasks.add(new Task("duck", "fold", "dollar", "bill", "Very Easy", 6, 97));
//        tasks.add(new Task("fountain", "baking", "pop", "soda", "Very Easy", 6, 92));
//        tasks.add(new Task("political", "surprise", "line", "party", "Very Easy", 7, 90));
//        tasks.add(new Task("food", "forward", "break", "fast", "Very Easy", 7, 82));
//        tasks.add(new Task("sleeping", "bean", "trash", "bag", "Very Easy", 8, 82));
//        tasks.add(new Task("water", "mine", "shaker", "salt", "Very Easy", 7, 85));
//        tasks.add(new Task("sandwich", "house", "golf", "club", "Very Easy", 8, 82));
//        tasks.add(new Task("nuclear", "feud", "album", "family", "Very Easy", 7, 85));
//        tasks.add(new Task("falling", "actor", "dust", "star", "Very Easy", 8, 79));
//        tasks.add(new Task("broken", "clear", "eye", "glass", "Very Easy", 8, 80));
//        tasks.add(new Task("skunk", "kings", "boiled", "cabbage", "Very Easy", 8, 76));
//        tasks.add(new Task("cottage", "swiss", "cake", "cheese", "Very Easy", 9, 70));
//        tasks.add(new Task("cream", "skate", "water", "ice", "Very Easy", 6, 90));
//        tasks.add(new Task("loser", "throat", "spot", "sore", "Very Easy", 9, 82));
//        tasks.add(new Task("show", "life", "row", "boat", "Very Easy", 10, 79));
//        tasks.add(new Task("rocking", "wheel", "high", "chair", "Very Easy", 8, 87));
//        tasks.add(new Task("preserve", "ranger", "tropical", "forest", "Very Easy", 9, 85));
//        tasks.add(new Task("cracker", "fly", "fighter", "fire", "Very Easy", 7, 89));
//        tasks.add(new Task("measure", "worm", "video", "tape", "Very Easy", 8, 87));
//        tasks.add(new Task("worm", "shelf", "end", "book", "Very Easy", 9, 85));
//
//        tasks.add(new Task("fur", "rack", "tail", "coat", "Easy", 10, 79));
//        tasks.add(new Task("river", "note", "account", "bank", "Easy", 10, 79));
//        tasks.add(new Task("flake", "mobile", "cone", "snow", "Easy", 10, 77));
//        tasks.add(new Task("mouse", "bear", "sand", "trap", "Easy", 11, 72));
//        tasks.add(new Task("widow", "bite", "monkey", "spider", "Easy", 11, 70));
//        tasks.add(new Task("bass", "complex", "sleep", "deep", "Easy", 13, 67));
//        tasks.add(new Task("coin", "quick", "spoon", "silver", "Easy", 10, 70));
//        tasks.add(new Task("gold", "stool", "tender", "bar", "Easy", 10, 75));
//        tasks.add(new Task("time", "hair", "stretch", "long", "Easy", 13, 67));
//        tasks.add(new Task("cadet", "capsule", "ship", "space", "Easy", 10, 74));
//        tasks.add(new Task("hound", "pressure", "shot", "blood", "Easy", 11, 72));
//        tasks.add(new Task("basket", "eight", "snow", "ball", "Easy", 11, 72));
//        tasks.add(new Task("print", "berry", "bird", "blue", "Easy", 9, 77));
//        tasks.add(new Task("safety", "cushion", "point", "pin", "Easy", 10, 72));
//        tasks.add(new Task("dream", "break", "light", "day", "Easy", 14, 65));
//        tasks.add(new Task("fish", "mine", "rush", "gold", "Easy", 10, 74));
//        tasks.add(new Task("high", "district", "house", "school", "Easy", 11, 74));
//        tasks.add(new Task("aid", "rubber", "wagon", "band", "Easy", 11, 70));
//
//        tasks.add(new Task("sage", "paint", "hair", "brush", "Medium", 14, 65));
//        tasks.add(new Task("french", "car", "shoe", "horn", "Medium", 13, 69));
//        tasks.add(new Task("peach", "arm", "tar", "pit", "Medium", 14, 67));
//        tasks.add(new Task("flower", "friend", "scout", "girl", "Medium", 15, 67));
//        tasks.add(new Task("sense", "courtesy", "place", "common", "Medium", 14, 67));
//        tasks.add(new Task("way", "board", "sleep", "walk", "Medium", 15, 64));
//        tasks.add(new Task("pie", "luck", "belly", "pot", "Medium", 19, 54));
//        tasks.add(new Task("opera", "hand", "dish", "soap", "Medium", 17, 62));
//        tasks.add(new Task("fox", "man", "peep", "hole", "Medium", 18, 64));
//        tasks.add(new Task("light", "birthday", "stick", "candle", "Medium", 24, 46));
//        tasks.add(new Task("shine", "beam", "struck", "moon", "Medium", 16, 62));
//        tasks.add(new Task("palm", "shoe", "house", "tree", "Medium", 19, 51));
//        tasks.add(new Task("wheel", "hand", "shopping", "cart", "Medium", 23, 49));
//        tasks.add(new Task("boot", "summer", "ground", "camp", "Medium", 18, 55));
//        tasks.add(new Task("mill", "tooth", "dust", "saw", "Medium", 21, 51));
//        tasks.add(new Task("main", "sweeper", "light", "street", "Medium", 16, 64));
//        tasks.add(new Task("pike", "coat", "signal", "turn", "Medium", 17, 63));
//        tasks.add(new Task("fly", "clip", "wall", "paper", "Medium", 26, 49));
//        tasks.add(new Task("age", "mile", "sand", "stone", "Medium", 25, 47));
//        tasks.add(new Task("catcher", "food", "hot", "dog", "Medium", 28, 42));
//        tasks.add(new Task("wagon", "break", "radio", "station", "Medium", 21, 51));
//        tasks.add(new Task("eight", "skate", "stick", "figure", "Medium", 19, 59));
//        tasks.add(new Task("down", "question", "check", "mark", "Medium", 20, 54));
//        tasks.add(new Task("dress", "dial", "flower", "sun", "Medium", 20, 51));
//        tasks.add(new Task("carpet", "alert", "ink", "red", "Medium", 18, 59));
//        tasks.add(new Task("master", "toss", "finger", "ring", "Medium", 19, 51));
//        tasks.add(new Task("hammer", "gear", "hunter", "head", "Medium", 18, 56));
//        tasks.add(new Task("knife", "light", "pal", "pen", "Medium", 17, 61));
//        tasks.add(new Task("blank", "list", "mate", "check", "Medium", 22, 51));
//        tasks.add(new Task("cat", "number", "phone", "call", "Medium", 21, 55));
//        tasks.add(new Task("keg", "puff", "room", "powder", "Medium", 19, 62));
//        tasks.add(new Task("type", "ghost", "screen", "writer", "Medium", 18, 54));
//        tasks.add(new Task("wet", "law", "business", "suit", "Medium", 17, 59));
//        tasks.add(new Task("horse", "human", "drag", "race", "Medium", 18, 56));
//        tasks.add(new Task("cracker", "union", "rabbit", "jack", "Medium", 19, 54));
//        tasks.add(new Task("bald", "screech", "emblem", "eagle", "Medium", 18, 56));
//        tasks.add(new Task("blood", "music", "cheese", "blue", "Medium", 17, 60));
//        tasks.add(new Task("manners", "round", "tennis", "table", "Medium", 17, 61));
//        tasks.add(new Task("off", "trumpet", "atomic", "blast", "Medium", 20, 55));
//        tasks.add(new Task("playing", "credit", "report", "card", "Medium", 19, 58));
//        tasks.add(new Task("rabbit", "cloud", "house", "white", "Medium", 19, 57));
//        tasks.add(new Task("room", "blood", "salts", "bath", "Medium", 19, 61));
//        tasks.add(new Task("salt", "deep", "foam", "sea", "Medium", 18, 57));
//        tasks.add(new Task("water", "tobacco", "stove", "pipe", "Medium", 19, 55));
//        tasks.add(new Task("square", "cardboard", "open", "box", "Medium", 17, 63));
//        tasks.add(new Task("ache", "hunter", "cabbage", "head", "Medium", 18, 55));
//        tasks.add(new Task("chamber", "staff", "box", "music", "Medium", 19, 53));
//        tasks.add(new Task("high", "book", "sour", "note", "Medium", 17, 58));
//        tasks.add(new Task("lick", "sprinkle", "mines", "salt", "Medium", 20, 52));
//        tasks.add(new Task("pure", "blue", "fall", "water", "Medium", 19, 59));
//        tasks.add(new Task("snack", "line", "birthday", "party", "Medium", 20, 53));
//        tasks.add(new Task("square", "telephone", "club", "book", "Medium", 19, 58));
//        tasks.add(new Task("surprise", "wrap", "care", "gift", "Medium", 20, 55));
//        tasks.add(new Task("ticket", "shop", "broker", "pawn", "Medium", 21, 52));
//        tasks.add(new Task("barrel", "root", "belly", "beer", "Medium", 21, 50));
//        tasks.add(new Task("blade", "witted", "weary", "dull", "Medium", 20, 51));
//        tasks.add(new Task("cherry", "time", "smell", "blossom", "Medium", 18, 58));
//        tasks.add(new Task("notch", "flight", "spin", "top", "Medium", 22, 52));
//        tasks.add(new Task("strap", "pocket", "time", "watch", "Medium", 21, 53));
//        tasks.add(new Task("walker", "main", "sweeper", "street", "Medium", 17, 58));
//        tasks.add(new Task("wicked", "bustle", "slicker", "city", "Medium", 18, 58));
//
//        tasks.add(new Task("piece", "mind", "dating", "game", "Hard", 22, 46));
//        tasks.add(new Task("date", "alley", "fold", "blind", "Hard", 25, 45));
//        tasks.add(new Task("stick", "maker", "point", "match", "Hard", 31, 25));
//        tasks.add(new Task("dust", "cereal", "fish", "bowl", "Hard", 24, 49));
//        tasks.add(new Task("office", "mail", "hat", "box", "Hard", 29, 29));
//        tasks.add(new Task("chamber", "mask", "natural", "gas", "Hard", 25, 44));
//        tasks.add(new Task("right", "cat", "carbon", "copy", "Hard", 24, 46));
//        tasks.add(new Task("cross", "rain", "tie", "bow", "Hard", 24, 46));
//        tasks.add(new Task("color", "numbers", "oil", "paint", "Hard", 16, 58));
//        tasks.add(new Task("mouse", "sharp", "blue", "cheese", "Hard", 19, 42));
//        tasks.add(new Task("sandwich", "golf", "foot", "club", "Hard", 20, 45));
//        tasks.add(new Task("silk", "cream", "even", "smooth", "Hard", 19, 41));
//        tasks.add(new Task("speak", "money", "street", "easy", "Hard", 22, 44));
//        tasks.add(new Task("big", "leaf", "shade", "tree", "Hard", 21, 40));
//        tasks.add(new Task("envy", "golf", "beans", "green", "Hard", 23, 40));
//        tasks.add(new Task("hall", "car", "swimming", "pool", "Hard", 22, 40));
//        tasks.add(new Task("ink", "herring", "neck", "red", "Hard", 21, 40));
//        tasks.add(new Task("measure", "desk", "scotch", "tape", "Hard", 22, 40));
//        tasks.add(new Task("strike", "same", "tennis", "match", "Hard", 24, 40));
//        tasks.add(new Task("athletes", "web", "rabbit", "foot", "Hard", 26, 35));
//        tasks.add(new Task("board", "magic", "death", "black", "Hard", 25, 36));
//        tasks.add(new Task("puss", "tart", "spoiled", "sour", "Hard", 21, 40));
//        tasks.add(new Task("rock", "times", "steel", "hard", "Hard", 22, 43));
//        tasks.add(new Task("stop", "petty", "sneak", "thief", "Hard", 18, 40));
//        tasks.add(new Task("thread", "pine", "pain", "needle", "Hard", 17, 42));
//        tasks.add(new Task("zone", "still", "noise", "quiet", "Hard", 22, 40));
//        tasks.add(new Task("cloth", "sad", "out", "sack", "Hard", 22, 41));
//        tasks.add(new Task("tank", "hill", "secret", "top", "Hard", 28, 38));
//        tasks.add(new Task("health ", "taker", "less", "care", "Hard", 20, 45));
//        tasks.add(new Task("lift", "card", "mask", "face", "Hard", 30, 33));
//        tasks.add(new Task("force", "line", "mail", "air", "Hard", 27, 31));
//        tasks.add(new Task("guy", "rain", "down", "fall", "Hard", 24, 41));
//        tasks.add(new Task("animal", "back", "rat", "pack", "Hard", 23, 49));
//        tasks.add(new Task("officer", "cash", "larceny", "petty", "Hard", 25, 44));
//        tasks.add(new Task("pine", "crab", "sauce", "apple", "Hard", 29, 33));
//        tasks.add(new Task("house", "thumb", "pepper", "green", "Hard", 24, 49));
//        tasks.add(new Task("foul", "ground", "mate", "play", "Hard", 25, 46));
//        tasks.add(new Task("change", "circuit", "cake", "short", "Hard", 26, 41));
//        tasks.add(new Task("tail", "water", "flood", "gate", "Hard", 27, 36));
//        tasks.add(new Task("marshal", "child", "piano", "grand", "Hard", 27, 38));
//        tasks.add(new Task("cover", "arm", "wear", "under", "Hard", 28, 36));
//        tasks.add(new Task("time", "blown", "nelson", "full", "Hard", 29, 30));
//        tasks.add(new Task("rain", "test", "stomach", "acid", "Hard", 27, 31));
//        tasks.add(new Task("pile", "market", "room", "stock", "Hard", 22, 44));
//        tasks.add(new Task("test", "runner", "map", "road", "Hard", 21, 44));
//        tasks.add(new Task("dive", "light", "rocket", "sky", "Hard", 32, 21));
//        tasks.add(new Task("man", "glue", "star", "super", "Hard", 23, 41));
//        tasks.add(new Task("tooth", "potato", "heart", "sweet", "Hard", 28, 28));
//        tasks.add(new Task("teeth", "arrest", "start", "false", "Hard", 21, 44));
//        tasks.add(new Task("iron", "shovel", "engine", "steam", "Hard", 19, 48));
//        tasks.add(new Task("rope", "truck", "line", "tow", "Hard", 31, 25));
//        tasks.add(new Task("off", "military", "first", "base", "Hard", 27, 31));
//        tasks.add(new Task("spoon", "cloth", "card", "table", "Hard", 29, 26));
//        tasks.add(new Task("cut", "cream", "war", "cold", "Hard", 33, 18));
//        tasks.add(new Task("note", "chain", "master", "key", "Hard", 28, 26));
//        tasks.add(new Task("shock", "shave", "taste", "after", "Hard", 27, 31));
//        tasks.add(new Task("grass", "king", "meat", "crab", "Hard", 29, 23));
//        tasks.add(new Task("baby", "spring", "cap", "shower", "Hard", 26, 28));
//        tasks.add(new Task("break", "bean", "cake", "coffee", "Hard", 25, 33));
//        tasks.add(new Task("hold", "print", "stool", "foot", "Hard", 24, 37));
//        tasks.add(new Task("roll", "bean", "fish", "jelly", "Hard", 30, 26));
//        tasks.add(new Task("oil", "bar", "tuna", "salad", "Hard", 27, 34));
//        tasks.add(new Task("bottom", "curve", "hop", "bell", "Hard", 25, 38));
//        tasks.add(new Task("tomato", "bomb", "picker", "cherry", "Hard", 24, 39));
//        tasks.add(new Task("pea", "shell", "chest", "nut", "Hard", 28, 23));
//        tasks.add(new Task("chocolate", "fortune", "tin", "cookie", "Hard", 17, 58));
//        tasks.add(new Task("lapse", "vivid", "elephant", "memory", "Hard", 28, 33));
//        tasks.add(new Task("board", "blade", "back", "switch", "Hard", 29, 29));
//        tasks.add(new Task("back", "step", "screen", "door", "Hard", 28, 33));
//
//        tasks.add(new Task("line", "fruit", "drunk", "punch", "Very Hard", 35, 10));
//        tasks.add(new Task("bump", "egg", "step", "goose", "Very Hard", 36, 8));
//        tasks.add(new Task("fight", "control", "machine", "gun", "Hard", 27, 28));
//        tasks.add(new Task("home", "arm", "room", "rest", "Hard", 29, 21));
//        tasks.add(new Task("child", "scan", "wash", "brain", "Very Hard", 30, 14));
//        tasks.add(new Task("nose", "stone", "bear", "brown", "Hard", 26, 26));
//        tasks.add(new Task("end", "line", "lock", "dead", "Very Hard", 35, 8));
//        tasks.add(new Task("control", "place", "rate", "birth", "Very Hard", 31, 14));
//        tasks.add(new Task("lounge", "hour", "napkin", "cocktail", "Very Hard", 33, 10));
//        tasks.add(new Task("artist", "hatch", "route", "escape", "Very Hard", 34, 12));
//        tasks.add(new Task("pet", "bottom", "garden", "rock", "Very Hard", 35, 11));
//        tasks.add(new Task("mate", "shoes", "total", "running", "Very Hard", 36, 9));
//        tasks.add(new Task("self", "attorney", "spending", "defense", "Very Hard", 38, 7));
//        tasks.add(new Task("land", "hand", "house", "farm", "Very Hard", 35, 6));
//        tasks.add(new Task("hungry", "order", "belt", "money", "Very Hard", 32, 9));
//        tasks.add(new Task("forward", "flush", "razor", "straight", "Very Hard", 31, 12));
//        tasks.add(new Task("shadow", "chart", "drop", "eye", "Very Hard", 29, 14));
//        tasks.add(new Task("way", "ground", "weather", "fair", "Very Hard", 30, 10));
//        tasks.add(new Task("cast", "side", "jump", "broad", "Very Hard", 35, 8));
//        tasks.add(new Task("reading", "service", "stick", "lip", "Very Hard", 34, 10));
//        tasks.add(new Task("over", "plant", "horse", "power", "Very Hard", 33, 12));
//        tasks.add(new Task("illness", "bus", "computer", "terminal", "Very Hard", 26, 18));
//        tasks.add(new Task("home", "sea", "bed", "sick", "Very Hard", 25, 29));
//        tasks.add(new Task("trip", "house", "goal", "field", "Very Hard", 31, 13));
//        tasks.add(new Task("fork", "dark", "man", "pitch", "Very Hard", 28, 18));
//        tasks.add(new Task("fence", "card", "master", "post", "Very Hard", 30, 13));
//        tasks.add(new Task("mail", "board", "lung", "black", "Very Hard", 29, 18));
//        tasks.add(new Task("cry", "front", "ship", "battle", "Very Hard", 30, 18));
//        tasks.add(new Task("wise", "work", "tower", "clock", " Very Hard", 31, 13));
//    }
//
//    public void openKeyboard(View v)
//    {
//        mKeyboardView.setVisibility(View.VISIBLE);
//        mKeyboardView.setEnabled(true);
//        if( v!=null)((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
//    }
//
//
//    public  void disableSoftInputFromAppearing( ) {
//
//        et.setRawInputType(InputType.TYPE_CLASS_TEXT);
//        et.setTextIsSelectable(true);
//    }
//
//    @Override
//    protected void onPause() {
//        if (!continueMusic) {
//            MusicManager.pause();
//        }
//        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//
//        opening = false;
//        if(musicon) {
//            continueMusic = false;
//            MusicManager.start(this, MusicManager.MUSIC_MENU);
//        }
//        super.onResume();
//
//    }
//
//    private void requestNewInterstitial() {
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mInterstitialAd.loadAd(adRequest);
//
//    }
//
//    @Override
//    public void onBackPressed() {
//
//        Intent myIntent = new Intent(Game.this, Start.class);
//        Game.this.startActivity(myIntent);
//    }
//
//}
