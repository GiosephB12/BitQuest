package com.example.bitquest;

import static android.content.Context.MODE_PRIVATE;

import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class InstructionFragmnet extends Fragment {
    private int step = 0;

    private String[] messages = {
            "Benvenuto nel minigame di logica!",
            "In questo gioco utilizzerai porte logiche AND e OR per accendere \nla lampadina.",
            "Ecco come funzionano:",
            "- Porta AND: Restituisce 1 solo se entrambe le entrate sono 1.",
            "Esempi: AND(1, 1) = 1, AND(1, 0) = 0, AND(0, 1) = 0, AND(0, 0) = 0",
            "- Porta OR: Restituisce 1 se almeno una delle entrate è 1.",
            "Esempi: OR(1, 1) = 1, OR(1, 0) = 1, OR(0, 1) = 1, OR(0, 0) = 0",
            "Obiettivo: Configura le porte in modo che l'output finale accenda \nla lampadina.",
            "Buona fortuna!"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_help, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        View overlay = view.findViewById(R.id.intro_overlay);
        TextView message = view.findViewById(R.id.intro_text);

        message.setText(messages[0]);

        overlay.setOnClickListener(v -> {
            step++;
            if (step < messages.length) {
                message.setText(messages[step]);
                SharedPreferences prefs = requireActivity().getSharedPreferences("notes", MODE_PRIVATE);
                prefs.edit().putBoolean("notion_" + 0, true).apply();
                prefs.edit().putBoolean("notion_" + 1, true).apply();
            } else {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .remove(this)
                        .commit();
            }
        });
    }
}
