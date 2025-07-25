package com.example.bitquest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Levels extends Fragment implements View.OnClickListener {
    int[] livelli;

    public Levels(){

    }

    public void setLivelli(int[] numeri) {
        this.livelli = numeri;
        return;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_livelli, container, false);
        ArrayList<View> allButtons;
        allButtons = (v.findViewById(R.id.LevelsTable)).getTouchables();

        int i=0;
        for (View element: allButtons) {
            Button b = (Button) element;
            if (b != null && livelli != null) {
                b.setOnClickListener(this);
                b.setText("" + livelli[i++]);
            }
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        Object tag = b.getTag();
    }
}
