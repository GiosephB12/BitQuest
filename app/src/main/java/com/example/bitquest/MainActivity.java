package com.example.bitquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button registrati;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.InputEmail);
        password = findViewById(R.id.InputPassword);
        /*controllo presenza dell'email e della password*/

    }

    public void Registrati(View view){
        try {
            Intent RegistrationPage = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(RegistrationPage);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Errore: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void Accesso(View view) {
        String enteredEmail = email.getText().toString().trim();
        String enteredPassword = password.getText().toString().trim();

        if (enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
            Toast.makeText(this, "Inserisci email e password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check se admin
        if (enteredEmail.equals("admin") && enteredPassword.equals("admin")) {
            Intent homePage = new Intent(getApplicationContext(), HomePage.class);
            homePage.putExtra("email", enteredEmail);
            homePage.putExtra("password", enteredPassword);
            startActivity(homePage);
            return;
        }

        // Altrimenti controlla le credenziali salvate
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedEmail = prefs.getString("email", "");
        String savedPassword = prefs.getString("password", "");

        if (enteredEmail.equals(savedEmail) && enteredPassword.equals(savedPassword)) {
            try {
                Intent homePage = new Intent(getApplicationContext(), HomePage.class);
                homePage.putExtra("email", enteredEmail);
                homePage.putExtra("password", enteredPassword);
                startActivity(homePage);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Errore: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Email o password non corretti", Toast.LENGTH_SHORT).show();
        }
    }
}