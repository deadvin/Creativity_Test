package com.upperhand.findthelink.objects;


public class Score {

    private int level;
    private boolean solved;
    private int time;
    private int pass;

    public Score(boolean solved, int time ,int level, int pass) {
        this.time = time;
        this.level = level;
        this.solved = solved;
        this.pass = pass;
    }

    public int getTime() {
        return time;
    }

    public boolean getSolved() {
        return solved;
    }

    public int getLevel() {
        return level;
    }

    public int getPass() {
        return pass;
    }


}