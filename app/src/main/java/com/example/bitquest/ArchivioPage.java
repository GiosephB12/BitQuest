package com.example.bitquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ArchivioPage extends AppCompatActivity {
    String email, password;
    private GridLayout gridLayout;
    private static final int NUM_ITEMS = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.archivio_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.archvio_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        Intent nicknameIntent = getIntent();
        email = nicknameIntent.getStringExtra("email");
        password = nicknameIntent.getStringExtra("password");

        TextView nickname = findViewById(R.id.nicknameText);
        String nicknamePref = prefs.getString("nickname", "");

        if (email.equals("admin") && password.equals("admin")) {
            nickname.setText("Admin");
        } else {
            nickname.setText(nicknamePref);
        }

        gridLayout = findViewById(R.id.grid_layout);
        populateGrid();

        ImageView logo = findViewById(R.id.logoImage);
        logo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchivioPage.this, HomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("email", email); //passa email
                intent.putExtra("password", password); //passa password
                startActivity(intent);
                finish();
            }
        });
    }

    private void populateGrid() {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < NUM_ITEMS; i++) {
            View itemView = inflater.inflate(R.layout.grid_item, gridLayout, false);

            itemView.post(() -> {
                int width = itemView.getWidth();
                ViewGroup.LayoutParams params = itemView.getLayoutParams();
                params.height = width;
                itemView.setLayoutParams(params);
            });

            ImageView image = itemView.findViewById(R.id.item_image);
            image.setImageResource(R.drawable.ic_lock);

            gridLayout.addView(itemView);
        }
    }

    public void goBack(View view){
        finish();
    }

    public void goToProfilo(View view){
        Intent ProfilePage = new Intent(getApplicationContext(), ProfiloPage.class);
        //aggiunto per controllo nickname
        ProfilePage.putExtra("email", email);
        ProfilePage.putExtra("password", password);

        startActivity(ProfilePage);
    }

    public void goToAppunti(View view){
        Intent intent = new Intent(getApplicationContext(), AppuntiPage.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);

        startActivity(intent);
    }
}

