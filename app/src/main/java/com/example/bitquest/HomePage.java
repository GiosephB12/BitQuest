package com.example.bitquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomePage extends AppCompatActivity {
    private String problem_solving ="Problem Solving";
    private String concetti_base = "Concetti Base di Informatica";
    private String concetti_avanzati = "Concetti Avanzati di Informatica";
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout problem = findViewById(R.id.card_problem_solving);
        TextView textProblem = problem.findViewById(R.id.cardTitle);
        textProblem.setText(problem_solving);
        TextView desctiptionProblem = problem.findViewById(R.id.cardDescription);
        desctiptionProblem.setText("Questa è la descrizione della categoria Problem Solving");

        LinearLayout concettiB = findViewById(R.id.card_base_info);
        TextView textConcettiB = concettiB.findViewById(R.id.cardTitle);
        textConcettiB.setText(concetti_base);
        TextView desctiptionConcettiB = concettiB.findViewById(R.id.cardDescription);
        desctiptionConcettiB.setText("Questa è la descrizione della categoria Concetti Base di Informatica");

        LinearLayout concettiA = findViewById(R.id.card_advanced_info);
        TextView textConcettiA = concettiA.findViewById(R.id.cardTitle);
        textConcettiA.setText(concetti_avanzati);
        TextView desctiptionConcettiA = concettiA.findViewById(R.id.cardDescription);
        desctiptionConcettiA.setText("Questa è la descrizione della categoria Concetti Avanzati di Informatica");

        // SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Gestione del nickname
        Intent nicknameIntent = getIntent();
        email = nicknameIntent.getStringExtra("email");
        password = nicknameIntent.getStringExtra("password");

        TextView nickname = findViewById(R.id.nicknameText);
        String nicknamePref = prefs.getString("nickname", "");

        if(email != null && email.equals("admin") && password != null && password.equals("admin")) {
            nickname.setText("Admin");
        } else {
            nickname.setText(nicknamePref);
        }

        boolean showIntro = prefs.getBoolean("introSignup", false);
        Log.d("HomePage", "showIntro = " + showIntro);
        // Controllo per mostrare intro con messaggi personalizzati
        if (!showIntro && !(email != null && email.equals("admin") && password != null && password.equals("admin"))) {
            String[] homeIntroMessages = {
                    "Benvenuto in BitQuest!",
                    "Io sono Bmo. Qui potrai imparare l'informatica giocando.",
                    "Scegli una categoria e inizia la tua avventura.",
                    "Buona fortuna!"
            };
            Bundle args = new Bundle();
            args.putStringArray("MESSAGES", homeIntroMessages);

            IntroFragment introFragment = new IntroFragment();
            introFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.home, introFragment)
                    .commit();

            prefs.edit().putBoolean("introSignup", true).apply();
        }
    }

    public void goToPuzzle(View view){
        LinearLayout category = (LinearLayout) view.getParent();
        TextView titleCategory = category.findViewById(R.id.cardTitle);
        String title = titleCategory.getText().toString();

        Intent puzzlePage = new Intent(getApplicationContext(), PuzzlePage.class);
        puzzlePage.putExtra("CATEGORY", title);
        // Passaggio credenziali
        puzzlePage.putExtra("email", email);
        puzzlePage.putExtra("password", password);
        startActivity(puzzlePage);
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
