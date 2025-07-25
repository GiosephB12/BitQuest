package com.example.bitquest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class IntroFragment extends Fragment {
    private int step = 0;

    private String[] messages = {
            "Benvenuto in BitQuest!",
            "Io sono Bmo. Qui potrai imparare l'informatica giocando.",
            "Scegli una categoria e inizia la tua avventura.",
            "Buona fortuna!"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro, container, false);
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
            } else {
                // Rimuovi il fragment
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .remove(this)
                        .commit();
            }
        });
    }
}
