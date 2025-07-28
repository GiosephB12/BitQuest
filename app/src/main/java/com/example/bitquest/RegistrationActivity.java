package com.example.bitquest;

import android.content.Intent;
import android.widget.EditText;
import android.content.SharedPreferences;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    EditText Name;
    EditText Cognome;
    EditText Nickname;
    EditText email;
    EditText password;
    EditText confermaPassword;
    Button registrati;
    ImageButton indietro;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registration_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.InputEmail);
        password = findViewById(R.id.InputPassword);
        confermaPassword = findViewById(R.id.InputConfermaPassword);
        Name = findViewById(R.id.InputNome);
        Cognome = findViewById(R.id.InputCognome);
        Nickname = findViewById(R.id.InputNickname);
        registrati = findViewById(R.id.SignUp);
        indietro = findViewById(R.id.Indietro);
    }

    public void TornaIndietro(View view){
        try {
            Intent AccessPage = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(AccessPage);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Errore: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void Registrati(View view) {
        String enteredNome = Name.getText().toString().trim();
        String enteredCognome = Cognome.getText().toString().trim();
        String enteredNickname = Nickname.getText().toString().trim();
        String enteredEmail = email.getText().toString().trim();
        String enteredPassword = password.getText().toString().trim();
        String confirmPassword = confermaPassword.getText().toString().trim();

        if (enteredNome.isEmpty() || enteredCognome.isEmpty() || enteredNickname.isEmpty()
                || enteredEmail.isEmpty() || enteredPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Compila tutti i campi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!enteredPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Conferma password errata. Riprova", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Reset di SharedPreferences per evitare conflitti da registrazioni precedenti
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            prefs.edit().clear().apply();

            // Salvataggio dei dati utente
            prefs.edit()
                    .putString("email", enteredEmail)
                    .putString("password", enteredPassword)
                    .putString("nickname", enteredNickname)
                    .putBoolean("introSignup", false)
                    .apply();

            Toast.makeText(this, "Registrazione completata!", Toast.LENGTH_SHORT).show();

            // Vai alla schermata Home
            Intent homePageIntent = new Intent(getApplicationContext(), HomePage.class);
            homePageIntent.putExtra("email", enteredEmail);
            homePageIntent.putExtra("password", enteredPassword);
            startActivity(homePageIntent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Errore durante la registrazione: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
