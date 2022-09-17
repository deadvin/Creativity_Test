package com.upperhand.findthelink.objects;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.upperhand.findthelink.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

public final class Utils {

    private static Toast toast;
    private static SharedPreferences.Editor editor;
    private static SharedPreferences preferences;
    private static Dialog customDialog;
    private static final ArrayList<Task> tasks = new ArrayList<>();


    public static void makeToast(String text, Context context){

        if (toast!= null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void buildAlertDialogue(int resource, Context context){

        customDialog = new Dialog(context);
        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(resource);
        customDialog.setCancelable(true);
        Window window = customDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        customDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

    }

    public static Dialog getAlertDialogue(){

        return customDialog;
    }

    public static void setSharedPref(String tag, boolean bool, Context context){
        editor = context.getSharedPreferences(tag, MODE_PRIVATE).edit();
        editor.putBoolean(tag, bool);
        editor.apply();
    }

    public static void setSharedPref(String tag, String string, Context context){
        editor = context.getSharedPreferences(tag, MODE_PRIVATE).edit();
        editor.putString(tag, string);
        editor.apply();
    }

    public static void setSharedPref(String tag, int num, Context context){
        editor = context.getSharedPreferences(tag, MODE_PRIVATE).edit();
        editor.putInt(tag, num);
        editor.apply();
    }

    public static int getSharedPref(String tag, int num, Context context){

        preferences = context.getSharedPreferences(tag, Context.MODE_PRIVATE);
        return preferences.getInt(tag, num);
    }

    public static boolean getSharedPref(String tag, boolean defaultVal, Context context){

        preferences = context.getSharedPreferences(tag, Context.MODE_PRIVATE);
        return preferences.getBoolean(tag, defaultVal);
    }

    public static String getSharedPref(String tag, String defaultVal, Context context){

        preferences = context.getSharedPreferences(tag, Context.MODE_PRIVATE);
        return preferences.getString(tag, defaultVal);
    }

    public static void loadTasks (){
        tasks.add(new Task("cane", "daddy", "plum", "sugar", "Very Easy", 6, 97));
        tasks.add(new Task("dew", "comb", "bee", "honey", "Very Easy", 6, 97));
        tasks.add(new Task("night", "wrist", "stop", "watch", "Very Easy", 6, 96));
        tasks.add(new Task("duck", "fold", "dollar", "bill", "Very Easy", 6, 97));
        tasks.add(new Task("fountain", "baking", "pop", "soda", "Very Easy", 6, 92));
        tasks.add(new Task("political", "surprise", "line", "party", "Very Easy", 7, 90));
        tasks.add(new Task("food", "forward", "break", "fast", "Very Easy", 7, 82));
        tasks.add(new Task("sleeping", "bean", "trash", "bag", "Very Easy", 8, 82));
        tasks.add(new Task("water", "mine", "shaker", "salt", "Very Easy", 7, 85));
        tasks.add(new Task("sandwich", "house", "golf", "club", "Very Easy", 8, 82));
        tasks.add(new Task("nuclear", "feud", "album", "family", "Very Easy", 7, 85));
        tasks.add(new Task("falling", "actor", "dust", "star", "Very Easy", 8, 79));
        tasks.add(new Task("broken", "clear", "eye", "glass", "Very Easy", 8, 80));
        tasks.add(new Task("skunk", "kings", "boiled", "cabbage", "Very Easy", 8, 76));
        tasks.add(new Task("cottage", "swiss", "cake", "cheese", "Very Easy", 9, 70));
        tasks.add(new Task("cream", "skate", "water", "ice", "Very Easy", 6, 90));
        tasks.add(new Task("loser", "throat", "spot", "sore", "Very Easy", 9, 82));
        tasks.add(new Task("show", "life", "row", "boat", "Very Easy", 10, 79));
        tasks.add(new Task("rocking", "wheel", "high", "chair", "Very Easy", 8, 87));
        tasks.add(new Task("preserve", "ranger", "tropical", "forest", "Very Easy", 9, 85));
        tasks.add(new Task("cracker", "fly", "fighter", "fire", "Very Easy", 7, 89));
        tasks.add(new Task("measure", "worm", "video", "tape", "Very Easy", 8, 87));
        tasks.add(new Task("worm", "shelf", "end", "book", "Very Easy", 9, 85));

        tasks.add(new Task("fur", "rack", "tail", "coat", "Easy", 10, 79));
        tasks.add(new Task("river", "note", "account", "bank", "Easy", 10, 79));
        tasks.add(new Task("flake", "mobile", "cone", "snow", "Easy", 10, 77));
        tasks.add(new Task("mouse", "bear", "sand", "trap", "Easy", 11, 72));
        tasks.add(new Task("widow", "bite", "monkey", "spider", "Easy", 11, 70));
        tasks.add(new Task("bass", "complex", "sleep", "deep", "Easy", 13, 67));
        tasks.add(new Task("coin", "quick", "spoon", "silver", "Easy", 10, 70));
        tasks.add(new Task("gold", "stool", "tender", "bar", "Easy", 10, 75));
        tasks.add(new Task("time", "hair", "stretch", "long", "Easy", 13, 67));
        tasks.add(new Task("cadet", "capsule", "ship", "space", "Easy", 10, 74));
        tasks.add(new Task("hound", "pressure", "shot", "blood", "Easy", 11, 72));
        tasks.add(new Task("basket", "eight", "snow", "ball", "Easy", 11, 72));
        tasks.add(new Task("print", "berry", "bird", "blue", "Easy", 9, 77));
        tasks.add(new Task("safety", "cushion", "point", "pin", "Easy", 10, 72));
        tasks.add(new Task("dream", "break", "light", "day", "Easy", 14, 65));
        tasks.add(new Task("fish", "mine", "rush", "gold", "Easy", 10, 74));
        tasks.add(new Task("high", "district", "house", "school", "Easy", 11, 74));
        tasks.add(new Task("aid", "rubber", "wagon", "band", "Easy", 11, 70));

        tasks.add(new Task("sage", "paint", "hair", "brush", "Medium", 14, 65));
        tasks.add(new Task("french", "car", "shoe", "horn", "Medium", 13, 69));
        tasks.add(new Task("peach", "arm", "tar", "pit", "Medium", 14, 67));
        tasks.add(new Task("flower", "friend", "scout", "girl", "Medium", 15, 67));
        tasks.add(new Task("sense", "courtesy", "place", "common", "Medium", 14, 67));
        tasks.add(new Task("way", "board", "sleep", "walk", "Medium", 15, 64));
        tasks.add(new Task("pie", "luck", "belly", "pot", "Medium", 19, 54));
        tasks.add(new Task("opera", "hand", "dish", "soap", "Medium", 17, 62));
        tasks.add(new Task("fox", "man", "peep", "hole", "Medium", 18, 64));
        tasks.add(new Task("light", "birthday", "stick", "candle", "Medium", 24, 46));
        tasks.add(new Task("shine", "beam", "struck", "moon", "Medium", 16, 62));
        tasks.add(new Task("palm", "shoe", "house", "tree", "Medium", 19, 51));
        tasks.add(new Task("wheel", "hand", "shopping", "cart", "Medium", 23, 49));
        tasks.add(new Task("boot", "summer", "ground", "camp", "Medium", 18, 55));
        tasks.add(new Task("mill", "tooth", "dust", "saw", "Medium", 21, 51));
        tasks.add(new Task("main", "sweeper", "light", "street", "Medium", 16, 64));
        tasks.add(new Task("pike", "coat", "signal", "turn", "Medium", 17, 63));
        tasks.add(new Task("fly", "clip", "wall", "paper", "Medium", 26, 49));
        tasks.add(new Task("age", "mile", "sand", "stone", "Medium", 25, 47));
        tasks.add(new Task("catcher", "food", "hot", "dog", "Medium", 28, 42));
        tasks.add(new Task("wagon", "break", "radio", "station", "Medium", 21, 51));
        tasks.add(new Task("eight", "skate", "stick", "figure", "Medium", 19, 59));
        tasks.add(new Task("down", "question", "check", "mark", "Medium", 20, 54));
        tasks.add(new Task("dress", "dial", "flower", "sun", "Medium", 20, 51));
        tasks.add(new Task("carpet", "alert", "ink", "red", "Medium", 18, 59));
        tasks.add(new Task("master", "toss", "finger", "ring", "Medium", 19, 51));
        tasks.add(new Task("hammer", "gear", "hunter", "head", "Medium", 18, 56));
        tasks.add(new Task("knife", "light", "pal", "pen", "Medium", 17, 61));
        tasks.add(new Task("blank", "list", "mate", "check", "Medium", 22, 51));
        tasks.add(new Task("cat", "number", "phone", "call", "Medium", 21, 55));
        tasks.add(new Task("keg", "puff", "room", "powder", "Medium", 19, 62));
        tasks.add(new Task("type", "ghost", "screen", "writer", "Medium", 18, 54));
        tasks.add(new Task("wet", "law", "business", "suit", "Medium", 17, 59));
        tasks.add(new Task("horse", "human", "drag", "race", "Medium", 18, 56));
        tasks.add(new Task("cracker", "union", "rabbit", "jack", "Medium", 19, 54));
        tasks.add(new Task("bald", "screech", "emblem", "eagle", "Medium", 18, 56));
        tasks.add(new Task("blood", "music", "cheese", "blue", "Medium", 17, 60));
        tasks.add(new Task("manners", "round", "tennis", "table", "Medium", 17, 61));
        tasks.add(new Task("off", "trumpet", "atomic", "blast", "Medium", 20, 55));
        tasks.add(new Task("playing", "credit", "report", "card", "Medium", 19, 58));
        tasks.add(new Task("rabbit", "cloud", "house", "white", "Medium", 19, 57));
        tasks.add(new Task("room", "blood", "salts", "bath", "Medium", 19, 61));
        tasks.add(new Task("salt", "deep", "foam", "sea", "Medium", 18, 57));
        tasks.add(new Task("water", "tobacco", "stove", "pipe", "Medium", 19, 55));
        tasks.add(new Task("square", "cardboard", "open", "box", "Medium", 17, 63));
        tasks.add(new Task("ache", "hunter", "cabbage", "head", "Medium", 18, 55));
        tasks.add(new Task("chamber", "staff", "box", "music", "Medium", 19, 53));
        tasks.add(new Task("high", "book", "sour", "note", "Medium", 17, 58));
        tasks.add(new Task("lick", "sprinkle", "mines", "salt", "Medium", 20, 52));
        tasks.add(new Task("pure", "blue", "fall", "water", "Medium", 19, 59));
        tasks.add(new Task("snack", "line", "birthday", "party", "Medium", 20, 53));
        tasks.add(new Task("square", "telephone", "club", "book", "Medium", 19, 58));
        tasks.add(new Task("surprise", "wrap", "care", "gift", "Medium", 20, 55));
        tasks.add(new Task("ticket", "shop", "broker", "pawn", "Medium", 21, 52));
        tasks.add(new Task("barrel", "root", "belly", "beer", "Medium", 21, 50));
        tasks.add(new Task("blade", "witted", "weary", "dull", "Medium", 20, 51));
        tasks.add(new Task("cherry", "time", "smell", "blossom", "Medium", 18, 58));
        tasks.add(new Task("notch", "flight", "spin", "top", "Medium", 22, 52));
        tasks.add(new Task("strap", "pocket", "time", "watch", "Medium", 21, 53));
        tasks.add(new Task("walker", "main", "sweeper", "street", "Medium", 17, 58));
        tasks.add(new Task("wicked", "bustle", "slicker", "city", "Medium", 18, 58));

        tasks.add(new Task("piece", "mind", "dating", "game", "Hard", 22, 46));
        tasks.add(new Task("date", "alley", "fold", "blind", "Hard", 25, 45));
        tasks.add(new Task("stick", "maker", "point", "match", "Hard", 31, 25));
        tasks.add(new Task("dust", "cereal", "fish", "bowl", "Hard", 24, 49));
        tasks.add(new Task("office", "mail", "hat", "box", "Hard", 29, 29));
        tasks.add(new Task("chamber", "mask", "natural", "gas", "Hard", 25, 44));
        tasks.add(new Task("right", "cat", "carbon", "copy", "Hard", 24, 46));
        tasks.add(new Task("cross", "rain", "tie", "bow", "Hard", 24, 46));
        tasks.add(new Task("color", "numbers", "oil", "paint", "Hard", 16, 58));
        tasks.add(new Task("mouse", "sharp", "blue", "cheese", "Hard", 19, 42));
        tasks.add(new Task("sandwich", "golf", "foot", "club", "Hard", 20, 45));
        tasks.add(new Task("silk", "cream", "even", "smooth", "Hard", 19, 41));
        tasks.add(new Task("speak", "money", "street", "easy", "Hard", 22, 44));
        tasks.add(new Task("big", "leaf", "shade", "tree", "Hard", 21, 40));
        tasks.add(new Task("envy", "golf", "beans", "green", "Hard", 23, 40));
        tasks.add(new Task("hall", "car", "swimming", "pool", "Hard", 22, 40));
        tasks.add(new Task("ink", "herring", "neck", "red", "Hard", 21, 40));
        tasks.add(new Task("measure", "desk", "scotch", "tape", "Hard", 22, 40));
        tasks.add(new Task("strike", "same", "tennis", "match", "Hard", 24, 40));
        tasks.add(new Task("athletes", "web", "rabbit", "foot", "Hard", 26, 35));
        tasks.add(new Task("board", "magic", "death", "black", "Hard", 25, 36));
        tasks.add(new Task("puss", "tart", "spoiled", "sour", "Hard", 21, 40));
        tasks.add(new Task("rock", "times", "steel", "hard", "Hard", 22, 43));
        tasks.add(new Task("stop", "petty", "sneak", "thief", "Hard", 18, 40));
        tasks.add(new Task("thread", "pine", "pain", "needle", "Hard", 17, 42));
        tasks.add(new Task("zone", "still", "noise", "quiet", "Hard", 22, 40));
        tasks.add(new Task("cloth", "sad", "out", "sack", "Hard", 22, 41));
        tasks.add(new Task("tank", "hill", "secret", "top", "Hard", 28, 38));
        tasks.add(new Task("health", "taker", "less", "care", "Hard", 20, 45));
        tasks.add(new Task("lift", "card", "mask", "face", "Hard", 30, 33));
        tasks.add(new Task("force", "line", "mail", "air", "Hard", 27, 31));
        tasks.add(new Task("guy", "rain", "down", "fall", "Hard", 24, 41));
        tasks.add(new Task("animal", "back", "rat", "pack", "Hard", 23, 49));
        tasks.add(new Task("officer", "cash", "larceny", "petty", "Hard", 25, 44));
        tasks.add(new Task("pine", "crab", "sauce", "apple", "Hard", 29, 33));
        tasks.add(new Task("house", "thumb", "pepper", "green", "Hard", 24, 49));
        tasks.add(new Task("foul", "ground", "mate", "play", "Hard", 25, 46));
        tasks.add(new Task("change", "circuit", "cake", "short", "Hard", 26, 41));
        tasks.add(new Task("tail", "water", "flood", "gate", "Hard", 27, 36));
        tasks.add(new Task("marshal", "child", "piano", "grand", "Hard", 27, 38));
        tasks.add(new Task("cover", "arm", "wear", "under", "Hard", 28, 36));
        tasks.add(new Task("time", "blown", "nelson", "full", "Hard", 29, 30));
        tasks.add(new Task("rain", "test", "stomach", "acid", "Hard", 27, 31));
        tasks.add(new Task("pile", "market", "room", "stock", "Hard", 22, 44));
        tasks.add(new Task("test", "runner", "map", "road", "Hard", 21, 44));
        tasks.add(new Task("dive", "light", "rocket", "sky", "Hard", 32, 21));
        tasks.add(new Task("man", "glue", "star", "super", "Hard", 23, 41));
        tasks.add(new Task("tooth", "potato", "heart", "sweet", "Hard", 28, 28));
        tasks.add(new Task("teeth", "arrest", "start", "false", "Hard", 21, 44));
        tasks.add(new Task("iron", "shovel", "engine", "steam", "Hard", 19, 48));
        tasks.add(new Task("rope", "truck", "line", "tow", "Hard", 31, 25));
        tasks.add(new Task("off", "military", "first", "base", "Hard", 27, 31));
        tasks.add(new Task("spoon", "cloth", "card", "table", "Hard", 29, 26));
        tasks.add(new Task("cut", "cream", "war", "cold", "Hard", 33, 18));
        tasks.add(new Task("note", "chain", "master", "key", "Hard", 28, 26));
        tasks.add(new Task("shock", "shave", "taste", "after", "Hard", 27, 31));
        tasks.add(new Task("grass", "king", "meat", "crab", "Hard", 29, 23));
        tasks.add(new Task("baby", "spring", "cap", "shower", "Hard", 26, 28));
        tasks.add(new Task("break", "bean", "cake", "coffee", "Hard", 25, 33));
        tasks.add(new Task("hold", "print", "stool", "foot", "Hard", 24, 37));
        tasks.add(new Task("roll", "bean", "fish", "jelly", "Hard", 30, 26));
        tasks.add(new Task("oil", "bar", "tuna", "salad", "Hard", 27, 34));
        tasks.add(new Task("bottom", "curve", "hop", "bell", "Hard", 25, 38));
        tasks.add(new Task("tomato", "bomb", "picker", "cherry", "Hard", 24, 39));
        tasks.add(new Task("pea", "shell", "chest", "nut", "Hard", 28, 23));
        tasks.add(new Task("chocolate", "fortune", "tin", "cookie", "Hard", 17, 58));
        tasks.add(new Task("lapse", "vivid", "elephant", "memory", "Hard", 28, 33));
        tasks.add(new Task("board", "blade", "back", "switch", "Hard", 29, 29));
        tasks.add(new Task("back", "step", "screen", "door", "Hard", 28, 33));

        tasks.add(new Task("line", "fruit", "drunk", "punch", "Very Hard", 35, 10));
        tasks.add(new Task("bump", "egg", "step", "goose", "Very Hard", 36, 8));
        tasks.add(new Task("fight", "control", "machine", "gun", "Hard", 27, 28));
        tasks.add(new Task("home", "arm", "room", "rest", "Hard", 29, 21));
        tasks.add(new Task("child", "scan", "wash", "brain", "Very Hard", 30, 14));
        tasks.add(new Task("nose", "stone", "bear", "brown", "Hard", 26, 26));
        tasks.add(new Task("end", "line", "lock", "dead", "Very Hard", 35, 8));
        tasks.add(new Task("control", "place", "rate", "birth", "Very Hard", 31, 14));
        tasks.add(new Task("lounge", "hour", "napkin", "cocktail", "Very Hard", 33, 10));
        tasks.add(new Task("artist", "hatch", "route", "escape", "Very Hard", 34, 12));
        tasks.add(new Task("pet", "bottom", "garden", "rock", "Very Hard", 35, 11));
        tasks.add(new Task("mate", "shoes", "total", "running", "Very Hard", 36, 9));
        tasks.add(new Task("self", "attorney", "spending", "defense", "Very Hard", 38, 7));
        tasks.add(new Task("land", "hand", "house", "farm", "Very Hard", 35, 6));
        tasks.add(new Task("hungry", "order", "belt", "money", "Very Hard", 32, 9));
        tasks.add(new Task("forward", "flush", "razor", "straight", "Very Hard", 31, 12));
        tasks.add(new Task("shadow", "chart", "drop", "eye", "Very Hard", 29, 14));
        tasks.add(new Task("way", "ground", "weather", "fair", "Very Hard", 30, 10));
        tasks.add(new Task("cast", "side", "jump", "broad", "Very Hard", 35, 8));
        tasks.add(new Task("reading", "service", "stick", "lip", "Very Hard", 34, 10));
        tasks.add(new Task("over", "plant", "horse", "power", "Very Hard", 33, 12));
        tasks.add(new Task("illness", "bus", "computer", "terminal", "Very Hard", 26, 18));
        tasks.add(new Task("home", "sea", "bed", "sick", "Very Hard", 25, 29));
        tasks.add(new Task("trip", "house", "goal", "field", "Very Hard", 31, 13));
        tasks.add(new Task("fork", "dark", "man", "pitch", "Very Hard", 28, 18));
        tasks.add(new Task("fence", "card", "master", "post", "Very Hard", 30, 13));
        tasks.add(new Task("mail", "board", "lung", "black", "Very Hard", 29, 18));
        tasks.add(new Task("cry", "front", "ship", "battle", "Very Hard", 30, 18));
        tasks.add(new Task("wise", "work", "tower", "clock", " Very Hard", 31, 13));
    }

    public static ArrayList<Task> getTasks() {

        return tasks;
    }

}