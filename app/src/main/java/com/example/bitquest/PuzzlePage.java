package com.example.bitquest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PuzzlePage extends AppCompatActivity {
    public HashMap<String, List<String>> puzzleMap= new HashMap<>();
    public List<String> problemSolving= new ArrayList<>();
    public List<String> concettiB= new ArrayList<>();
    public List<String> concettiA = new ArrayList<>();
    public String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.puzzle_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.puzzle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        problemSolving.add("If Than Else");
        problemSolving.add("Eletric Escape");
        problemSolving.add("Circuit Caper");

        concettiB.add("Bit Hopper");
        concettiB.add("Code Grid");
        concettiB.add("Debug Dash");

        concettiA.add("Stack & Maze");
        concettiA.add("CAlgoForge");
        concettiA.add("Hacknetic");

        puzzleMap.put("Problem Solving", problemSolving);
        puzzleMap.put("Concetti Base di Informatica", concettiB);
        puzzleMap.put("Concetti Avanzati di Informatica", concettiA);

        Intent intent = getIntent();
        category = intent.getStringExtra("CATEGORY");
        TextView titleCategory = findViewById(R.id.TitleCategory);
        titleCategory.setText(category);
        List<String> puzzles = puzzleMap.get(category);

        ListView puzzleList = findViewById(R.id.puzzleList);

        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.list_puzzle, new ArrayList<String>());
        puzzleList.setAdapter(customAdapter);

        for (String s : puzzles){
            customAdapter.add(s);
        }

        ImageView logo = findViewById(R.id.logoImage);
        logo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PuzzlePage.this, HomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    public void goLevels(View view){
        TextView type = (TextView) view.findViewById(R.id.singlePuzzle);
        String typePuzzle = ""+type.getText();
        Intent levelPage = new Intent(getApplicationContext(), LevelsPage.class);
        levelPage.putExtra("CATEGORY", category);
        levelPage.putExtra("TYPE", typePuzzle);
        startActivity(levelPage);
    }
    public void goBack(View view){
        finish();
    }
}
