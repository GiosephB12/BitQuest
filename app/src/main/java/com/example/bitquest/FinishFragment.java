package com.example.bitquest;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class FinishFragment extends Fragment {
    private int step = 0;

    private String[] messages = {
            "IBM è un'azienda tecnologica \nglobale specializzata in cloud,\n" +
                    "intelligenza artificiale e \nservizi IT.",
            "Il primo Bug: Sai che il primo \nbug che fù ritrovato era prorio\n" +
                    "un'insetto, eh si, si trovava proprio in uno dei primi computer"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.finish_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        View overlay = view.findViewById(R.id.intro_overlay);
        TextView message = view.findViewById(R.id.notionText);
        Random choose = new Random();
        int pos = choose.nextInt(2);
        message.setText(messages[pos]);

        SharedPreferences prefs = requireActivity().getSharedPreferences("curiosity_prefs", MODE_PRIVATE);
        prefs.edit().putBoolean("curiosity_" + pos, true).apply();


    }
}