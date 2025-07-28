package com.example.bitquest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
    public String category, email, password;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.puzzle_page);

        // Padding per system bars (notch, nav bar...)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.puzzle), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inizializzo i puzzle per ogni categoria
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

        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.list_puzzle, new ArrayList<>());
        puzzleList.setAdapter(customAdapter);

        for (String s : puzzles){
            customAdapter.add(s);
        }

        // Gestione nickname e credenziali
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        TextView nickname = findViewById(R.id.nicknameText);
        String nicknamePref = prefs.getString("nickname", "");

        if(email != null && email.equals("admin") && password != null && password.equals("admin")) {
            nickname.setText("Admin");
        }
        else {
            nickname.setText(nicknamePref);
        }

        // Controllo se mostrare intro (solo se non admin e non mostrato prima)
        boolean introShown = prefs.getBoolean("introPuzzleShown", false);

        if(!introShown && !(email != null && email.equals("admin") && password != null && password.equals("admin"))) {
            String[] puzzleIntroMessages = {
                    "Ottimo! Ora scegli la tipologia che ti ispira di più,",
                    "e continua la tua avventura\n nel fantastico mondo\n dell’informatica.",
                    "Stai andando alla grande!"
            };

            Bundle args = new Bundle();
            args.putStringArray("MESSAGES", puzzleIntroMessages);

            IntroFragment introFragment = new IntroFragment();
            introFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.puzzle, introFragment)
                    .commit();

            prefs.edit().putBoolean("introPuzzleShown", true).apply();
        }

        // Logo torna alla HomePage
        ImageView logo = findViewById(R.id.logoImage);
        logo.setOnClickListener(v -> {
            Intent homeIntent = new Intent(PuzzlePage.this, HomePage.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            homeIntent.putExtra("email", email);
            homeIntent.putExtra("password", password);
            startActivity(homeIntent);
            finish();
        });
    }

    public void goLevels(View view){
        TextView type = view.findViewById(R.id.singlePuzzle);
        String typePuzzle = type.getText().toString();

        Intent levelPage = new Intent(getApplicationContext(), LevelsPage.class);
        levelPage.putExtra("CATEGORY", category);
        levelPage.putExtra("TYPE", typePuzzle);

        levelPage.putExtra("email", email);
        levelPage.putExtra("password", password);
        startActivity(levelPage);
    }

    public void goBack(View view){
        finish();
    }

    public void goToProfilo(View view){
        Intent profilePage = new Intent(getApplicationContext(), ProfiloPage.class);
        profilePage.putExtra("email", email);
        profilePage.putExtra("password", password);
        startActivity(profilePage);
    }

    public void goToAppunti(View view){
        Intent intent = new Intent(getApplicationContext(), AppuntiPage.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    public void goToArchivio(View view){
        Intent profilePage = new Intent(getApplicationContext(), ArchivioPage.class);
        profilePage.putExtra("email", email);
        profilePage.putExtra("password", password);
        startActivity(profilePage);
    }

}
