package com.example.bitquest;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class ArchivioFragment extends Fragment {

    private int imageResId;
    private int pos;
    private String[] descriptionNotions ={
            "IBM (International Business Machines Corporation) è una " +
            "multinazionale tecnologica americana fondata nel 1911. " +
            "È specializzata in hardware, software, servizi IT e soluzioni" +
            " di intelligenza artificiale. IBM è nota per le sue innovazioni storiche, " +
            "come il mainframe, e per lo sviluppo della piattaforma di AI Watson.",
            "Il primo bug informatico fu scoperto nel 1947 quando gli ingegneri del computer Harvard Mark II trovarono " +
            "una falena bloccata in un relè. Annotarono l’evento nel registro come il " +
            "\"first actual case of bug being found\", dando origine al termine \"bug\" in informatica. " +
            "Da allora, il termine è usato per indicare errori nei programmi."
    };
    public ArchivioFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            imageResId = getArguments().getInt("image_res_id", R.drawable.ic_lock);
            pos = getArguments().getInt("description");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notion_fragment, container, false);
        ImageView curiosityImage = view.findViewById(R.id.image_notion);
        curiosityImage.setImageResource(imageResId);
        ImageView closeButton = view.findViewById(R.id.close_fragment);
        closeButton.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .remove(ArchivioFragment.this)
                    .commit();
        });
        TextView description = view.findViewById(R.id.notion_description);
        description.setText(descriptionNotions[pos]);
        return view;
    }

}

