package com.upperhand.findthelink;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.upperhand.findthelink.objects.Task;
import com.upperhand.findthelink.objects.TaskAdapter;
import com.upperhand.findthelink.objects.Utils;

import java.util.ArrayList;


public class TestsFragment extends Fragment {

    final ArrayList<Task> tasks = new ArrayList<>();
    int level;
    TaskAdapter adapter;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_tests, container, false);

        adapter = new TaskAdapter((Activity)context, tasks);
        ListView listView =  view.findViewById(R.id.lv);
        listView.setAdapter(adapter);

        return view;
    }



    public void reset() {
        level = Utils.getSharedPref("level", 0, context);
        int donTasksCount = level;
        for (int i = 0; i < donTasksCount; i++) {
            tasks.add(Utils.getTasks().get(i));
        }
        adapter.notifyDataSetChanged();
    }
}