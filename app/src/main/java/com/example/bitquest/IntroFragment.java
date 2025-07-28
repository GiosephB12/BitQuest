package com.example.bitquest;

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
    private String[] messages;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        View overlay = view.findViewById(R.id.intro_overlay);
        TextView message = view.findViewById(R.id.intro_text);

        Bundle args = getArguments();
        if (args != null && args.containsKey("MESSAGES")) {
            messages = args.getStringArray("MESSAGES");
        }

        if (messages == null || messages.length == 0) {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .remove(this)
                    .commit();
            return view;
        }

        message.setText(messages[0]);

        overlay.setOnClickListener(v -> {
            step++;
            if (step < messages.length) {
                message.setText(messages[step]);
            } else {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .remove(this)
                        .commit();
            }
        });

        return view;
    }

}
