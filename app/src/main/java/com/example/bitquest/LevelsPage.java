package com.example.bitquest;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
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
    public String email, password;
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

        email = level.getStringExtra("email");
        password = level.getStringExtra("password");

        SharedPreferences prefs;
        if ("admin".equals(email) && "admin".equals(password)) {
            prefs = getSharedPreferences("AdminPrefs", MODE_PRIVATE);
        } else {
            prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        }

        TextView titleCategory = findViewById(R.id.TitleCategory);
        TextView typePuzzle = findViewById(R.id.Type);
        TextView nickname = findViewById(R.id.nicknameText);
        String nicknamePref = prefs.getString("nickname", "");

        if (email != null && email.equals("admin") && password != null && password.equals("admin")) {
            nickname.setText("Admin");
        } else {
            nickname.setText(nicknamePref);
        }

        boolean introShown = prefs.getBoolean("introLevelsShown", false);

        if(!introShown && !(email != null && email.equals("admin") && password != null && password.equals("admin"))){
            String[] levelsIntroMessages = {
                    "Perfetto! Ora scegli il livello\n di difficoltà che preferisci.",
                    "Puoi iniziare da Facile,\n oppure\n metterti alla prova con:\n Normale o Difficile.",
                    "Se è la tua prima volta,\n ti consiglio di partire\n da Facile.",
                    "Pronto? L’avventura continua!"
            };

            // Lancia IntroFragment con messaggi
            Bundle bundle = new Bundle();
            bundle.putStringArray("MESSAGES", levelsIntroMessages);
            IntroFragment introFragment = new IntroFragment();
            introFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.levelsPuzzle, introFragment)
                    .commit();

            // Salva che è stato mostrato
            prefs.edit().putBoolean("introLevelsShown", true).apply();
        }

        // Imposta i titoli
        titleCategory.setText(category);
        typePuzzle.setText(type);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < 3; i++) {
            Fragment livello = new Levels();
            ((Levels) livello).setLivelli(levels[i]);
            ((Levels) livello).setType(type);
            ((Levels) livello).setEmail(email);
            ((Levels) livello).setPassword(password);

            String str_id = String.format("frameLevels%d", i);
            int containerId = getResources().getIdentifier(str_id, "id", getPackageName());
            ft.add(containerId, livello, null);
        }
        ft.commit();

        ImageView logo = findViewById(R.id.logoImage);
        logo.setOnClickListener(v -> {
            Intent intent = new Intent(LevelsPage.this, HomePage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            startActivity(intent);
            finish();
        });
    }

    public void goBack(View view) {
        finish();
    }

    public void goToProfilo(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfiloPage.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    public void goToAppunti(View view) {
        Intent intent = new Intent(getApplicationContext(), AppuntiPage.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    public void goToArchivio(View view) {
        Intent intent = new Intent(getApplicationContext(), ArchivioPage.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }
}
