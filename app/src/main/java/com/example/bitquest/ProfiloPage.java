package com.example.bitquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfiloPage extends AppCompatActivity {
    String email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profilo_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profilo), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView nozioni = findViewById(R.id.edit_nozioni);
        TextView appunti = findViewById(R.id.edit_appunti);
        TextView livelli = findViewById(R.id.edit_livelli);
        LinearLayout logout = findViewById(R.id.logout_container);


        //Gestione del nickname
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        Intent nicknameIntent = getIntent();
        email = nicknameIntent.getStringExtra("email");
        password = nicknameIntent.getStringExtra("password");

        TextView nickname = findViewById(R.id.nicknameText);
        String nicknamePref = prefs.getString("nickname", "");

        if(email.equals("admin") && password.equals("admin")) {
            nickname.setText("Admin");
        }
        else
            nickname.setText(nicknamePref);


        ImageView logo = findViewById(R.id.logoImage);
        logo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfiloPage.this, HomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("email", email);       // Passa email
                intent.putExtra("password", password); // Passa password
                startActivity(intent);
                finish();
            }
        });
    }

    public void Logout(View view) {
        try {
            Intent AccessPage = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(AccessPage);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Errore: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void goBack(View view){
        finish();
    }

    public void goToProfilo(View view){
        Toast.makeText(this, "Sei già nella sezione del profilo utente", Toast.LENGTH_SHORT).show();
    }
}
