package com.example.bitquest;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LevelsPage extends AppCompatActivity {
    public String category;
    public String type;
    public int[][] levels = new int[3][10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.levels_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.levelsPuzzle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        int count = 1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                levels[i][j] = count++;
            }
        }

        Intent level = getIntent();
        category = level.getStringExtra("CATEGORY");
        type = level.getStringExtra("TYPE");
        TextView titleCategory = findViewById(R.id.TitleCategory);
        TextView typePuzzle = findViewById(R.id.Type);

        titleCategory.setText(category);
        typePuzzle.setText(type);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for (int i=0; i<3; i++){
            Fragment livello = new Levels();
            ((Levels) livello).setLivelli(levels[i]);
            String str_id = String.format("frameLevels%d",i);
            int containerId = getResources().getIdentifier(str_id, "id", getPackageName());
            ft.add(containerId, livello, null);
        }
        ft.commit();


        ImageView logo = findViewById(R.id.logoImage);
        logo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelsPage.this, HomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    public void goBack(View view){
        finish();
    }
}
