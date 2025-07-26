package com.example.bitquest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

public class Levels extends Fragment implements View.OnClickListener {
    int[] livelli;
    String type;
    public Levels(){

    }

    public void setLivelli(int[] numeri) {
        this.livelli = numeri;
    }
    public void setType(String type){
        this.type = type;
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
        String number_level = ""+ b.getText();
        if(number_level.equals("1") && type.equals("Circuit Caper")){
            Intent game = new Intent(getContext(), GamePage.class);
            game.putExtra("Level", number_level);
            startActivity(game);
        }else {
            View rootView = getActivity().findViewById(R.id.levelsPuzzle);

            if (rootView instanceof RelativeLayout) {
                RelativeLayout parentLayout = (RelativeLayout) rootView;

                FrameLayout overlay = new FrameLayout(getContext());
                overlay.setBackgroundResource(R.drawable.intro_text);

                TextView coomingSoon = new TextView(getContext());
                coomingSoon.setText("Coming Soon");
                coomingSoon.setTextColor(getResources().getColor(R.color.green_black));
                Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.vt323);
                coomingSoon.setTypeface(typeface);
                coomingSoon.setTextSize(30);

                FrameLayout.LayoutParams textParams = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                );
                textParams.gravity = Gravity.CENTER;
                coomingSoon.setLayoutParams(textParams);
                overlay.addView(coomingSoon);

                ImageView closeIcon = new ImageView(getContext());
                closeIcon.setImageResource(R.drawable.ic_exit);

                int iconSize = 100;
                FrameLayout.LayoutParams closeParams = new FrameLayout.LayoutParams(
                        iconSize,
                        iconSize
                );
                closeParams.gravity = Gravity.END | Gravity.TOP;
                closeParams.setMargins(0, 16, 16, 0);
                closeIcon.setLayoutParams(closeParams);

                closeIcon.setOnClickListener(view -> parentLayout.removeView(overlay));
                overlay.addView(closeIcon);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, 250);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

                overlay.setLayoutParams(layoutParams);

                overlay.setClickable(true);
                parentLayout.addView(overlay);
            }
        }
    }
}
