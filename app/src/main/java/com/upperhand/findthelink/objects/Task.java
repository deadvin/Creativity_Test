package com.upperhand.findthelink.objects;


public class Task {

    private String hint1;
    private String hint2;
    private String hint3;
    private String answer;
    private int time;
    private int solved;
    private String dif;

    public Task(String hint1, String hint2, String hint3, String answer , String dif, int time , int solved)

    {
        this.hint1 = hint1;
        this.hint2 = hint2;
        this.hint3 = hint3;
        this.answer = answer;
        this.time = time;
        this.dif = dif;
        this.solved = solved;
    }

    public String getHint1() {
        return hint1;
    }

    public String getHint2() {
        return hint2;
    }

    public String getHint3() {
        return hint3;
    }

    public String getAnswer() {
        return answer;
    }

    public String getDif() {
        return dif;
    }

    public int getTime() {
        return time;
    }

    public int getSolved() {
        return solved;
    }

}