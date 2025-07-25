package com.example.bitquest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    private LayoutInflater inflater;

    public CustomAdapter(Context context, int resourceId, List<String> objects) {
        super(context, resourceId, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            v = inflater.inflate(R.layout.list_puzzle, null);
        }
        String puzzle = getItem(position);
        TextView puzzleView = (TextView) v.findViewById(R.id.singlePuzzle);
        puzzleView.setText(puzzle);

        return v;
    }
}