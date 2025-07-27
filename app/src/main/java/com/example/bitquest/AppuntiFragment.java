package com.example.bitquest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AppuntiFragment extends Fragment {

    private int imageResId;
    private int pos;
    private String[] descriptionNote ={
            "Le porte AND sono componenti logici fondamentali nell'elettronica digitale. " +
            "Producono un'uscita alta (1) solo quando tutti i loro ingressi sono alti (1). " +
            "Sono usate per eseguire operazioni logiche che richiedono condizioni simultanee.",
            "Le porte OR sono componenti logici utilizzate nei circuiti digitali. Forniscono un'uscita alta (1) " +
            "se almeno uno degli ingressi è alto (1)." +
            " Sono utili per eseguire operazioni in cui basta una sola condizione vera."
    };
    public AppuntiFragment() {}


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
        View view = inflater.inflate(R.layout.notes_fragment, container, false);
        ImageView curiosityImage = view.findViewById(R.id.image_notion);
        curiosityImage.setImageResource(imageResId);
        ImageView closeButton = view.findViewById(R.id.close_fragment);
        closeButton.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .remove(AppuntiFragment.this)
                    .commit();
        });
        TextView description = view.findViewById(R.id.note_description);
        description.setText(descriptionNote[pos]);
        return view;
    }

}
