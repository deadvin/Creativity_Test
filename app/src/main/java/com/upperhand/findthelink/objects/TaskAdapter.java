package com.upperhand.findthelink.objects;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.upperhand.findthelink.R;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {
    
    public TaskAdapter(Activity context, ArrayList<Task> desserts) {
        super(context, 0, desserts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.row, parent, false);
        }

        Task curTask = getItem(position);

        TextView hint1 =  listItemView.findViewById(R.id.hint1);
        hint1.setText(curTask.getHint1());

        TextView hint2 =  listItemView.findViewById(R.id.hint2);
        hint2.setText(curTask.getHint2());

        TextView hint3 =  listItemView.findViewById(R.id.hint3);
        hint3.setText(curTask.getHint3());

        TextView answer =  listItemView.findViewById(R.id.answer);
        answer.setText(curTask.getAnswer());

        return listItemView;
    }
}