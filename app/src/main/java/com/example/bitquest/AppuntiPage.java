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

public class AppuntiPage extends AppCompatActivity {
    String email, password;
    private GridLayout gridLayout;
    private static final int NUM_ITEMS = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.appunti_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.appunti_layout), (v, insets) -> {
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
                Intent intent = new Intent(AppuntiPage.this, HomePage.class);
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
        SharedPreferences note = getSharedPreferences("notes", MODE_PRIVATE);
        int unlockedCount = 0; // nuovo contatore

        for (int i = 0; i < NUM_ITEMS; i++) {
            View itemView = inflater.inflate(R.layout.grid_item, gridLayout, false);

            itemView.post(() -> {
                int width = itemView.getWidth();
                ViewGroup.LayoutParams params = itemView.getLayoutParams();
                params.height = width;
                itemView.setLayoutParams(params);
            });

            ImageView image = itemView.findViewById(R.id.item_image);
            boolean unlocked = note.getBoolean("notion_" + i, false);

            int imageResId;
            int pos;
            if (unlocked) {
                unlockedCount++; // incremento contatore

                if (i == 0) {
                    imageResId = R.drawable.and_gate;
                } else if (i == 1) {
                    imageResId = R.drawable.or_gate;
                } else {
                    imageResId = R.drawable.ic_lock;
                }

                image.setImageResource(imageResId);
                pos = i;

                itemView.setOnClickListener(v -> {
                    AppuntiFragment fragment = new AppuntiFragment();
                    Bundle args = new Bundle();
                    args.putInt("image_res_id", imageResId);
                    args.putInt("description", pos);
                    fragment.setArguments(args);

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.appunti_layout, fragment)
                            .commit();
                });

            } else {
                imageResId = R.drawable.ic_lock;
            }

            gridLayout.addView(itemView);
        }

        // Salva il contatore negli SharedPreferences
        SharedPreferences prefs;
        if ("admin".equals(email) && "admin".equals(password)) {
            prefs = getSharedPreferences("AdminPrefs", MODE_PRIVATE);
        } else {
            prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("unlocked_count", unlockedCount);
        editor.apply();
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

    public void goToArchivio(View view){
        Intent ProfilePage = new Intent(getApplicationContext(), ArchivioPage.class);
        //aggiunto per controllo nickname
        ProfilePage.putExtra("email", email);
        ProfilePage.putExtra("password", password);

        startActivity(ProfilePage);
    }

    public void goToAppunti(View view){

    }
}
