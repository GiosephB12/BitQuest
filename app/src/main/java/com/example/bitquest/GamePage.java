package com.example.bitquest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewParent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GamePage extends AppCompatActivity {
    private ImageView andGate, orGate;
    private FrameLayout positionOne, positionTwo, positionThree;
    private TextView inputOne;
    private TextView inputThree;
    private boolean validateSecondInput = false;
    boolean firstTime = true;

    private float dX, dY;
    private View draggedView = null;
    private ViewGroup rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.game_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Nickname setup
        TextView nicknameText = findViewById(R.id.nicknameText); // Assicurati che esista nel layout
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String nickname = prefs.getString("nickname", "");
        Intent nick = getIntent();
        String email = nick.getStringExtra("email");
        String password = nick.getStringExtra("password");

        if (email != null && email.equals("admin") && password != null && password.equals("admin")) {
            nicknameText.setText("Admin");
        } else {
            nicknameText.setText(nickname);
        }

        ImageView logo = findViewById(R.id.logoImage);
        logo.setOnClickListener(v -> {
            Intent intent = new Intent(GamePage.this, HomePage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        if (firstTime) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.game, new InstructionFragmnet())
                    .commit();
        }

        andGate = findViewById(R.id.and_gate);
        orGate = findViewById(R.id.or_gate);

        positionOne = findViewById(R.id.positionOne);
        positionTwo = findViewById(R.id.positionTwo);
        positionThree = findViewById(R.id.positionThree);
        inputThree = findViewById(R.id.finalValue);
        inputOne = findViewById(R.id.valueRight);
        setCloneDragListener(andGate, R.id.num_and);
        setCloneDragListener(orGate, R.id.num_or);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setCloneDragListener(ImageView originalGate, int id) {
        originalGate.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                TextView replace = findViewById(id);
                String text = replace.getText().toString();

                int num_gate = 0;
                if (text.contains("X")) {
                    String[] parts = text.split("X");
                    if (parts.length == 2) {
                        try {
                            num_gate = Integer.parseInt(parts[1].trim());
                        } catch (NumberFormatException ignored) {}
                    }
                }

                if (num_gate <= 0) return false;

                num_gate--;
                if (text.contains("AND")) {
                    replace.setText("AND X" + num_gate);
                } else if (text.contains("OR")) {
                    replace.setText("OR X" + num_gate);
                } else {
                    replace.setText("X" + num_gate);
                }

                ImageView clone = new ImageView(this);
                clone.setImageDrawable(((ImageView) v).getDrawable());
                clone.setTag(text.contains("AND") ? "AND" : text.contains("OR") ? "OR" : "UNKNOWN");

                int width = v.getWidth();
                int height = v.getHeight();
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
                clone.setLayoutParams(params);

                rootLayout = findViewById(R.id.game);
                rootLayout.addView(clone);

                clone.setX(event.getRawX() - width);
                clone.setY(event.getRawY() - height);

                setDragLogic(clone);
                draggedView = clone;

                return true;
            }
            return false;
        });
    }
    @SuppressLint("ClickableViewAccessibility")
    private void setDragLogic(View view) {
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    dX = v.getX() - event.getRawX();
                    dY = v.getY() - event.getRawY();
                    return true;

                case MotionEvent.ACTION_MOVE:
                    v.animate()
                            .x(event.getRawX() + dX)
                            .y(event.getRawY() + dY)
                            .setDuration(0)
                            .start();
                    return true;

                case MotionEvent.ACTION_UP:
                    checkDrop(v);
                    return true;

                default:
                    return false;
            }
        });
    }

    private void checkDrop(View draggedView) {
        FrameLayout[] targets = {positionOne, positionTwo, positionThree};
        boolean droppedInTarget = false;

        for (FrameLayout target : targets) {
            int[] targetLoc = new int[2];
            target.getLocationOnScreen(targetLoc);
            Rect targetRect = new Rect(
                    targetLoc[0],
                    targetLoc[1],
                    targetLoc[0] + target.getWidth(),
                    targetLoc[1] + target.getHeight()
            );

            int[] viewLoc = new int[2];
            draggedView.getLocationOnScreen(viewLoc);
            int centerX = viewLoc[0] + draggedView.getWidth() / 2;
            int centerY = viewLoc[1] + draggedView.getHeight() / 2;

            if (targetRect.contains(centerX, centerY)) {
                if (draggedView.getParent() instanceof ViewGroup) {
                    ((ViewGroup) draggedView.getParent()).removeView(draggedView);
                }

                target.removeAllViews();
                target.addView(draggedView);

                int sizeInDp = 70;
                float scale = draggedView.getContext().getResources().getDisplayMetrics().density;
                int sizePx = (int) (sizeInDp * scale + 0.5f);

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(sizePx, sizePx);
                draggedView.setLayoutParams(params);
                draggedView.setPivotX(sizePx / 2f);
                draggedView.setPivotY(sizePx / 2f);

                draggedView.setScaleX(0.5f);
                draggedView.setScaleY(0.5f);
                draggedView.setRotation(0f);

                draggedView.animate()
                        .x(3)
                        .y(0)
                        .scaleX(1f)
                        .scaleY(1f)
                        .rotation(90f)
                        .setDuration(500)
                        .start();
                droppedInTarget = true;
                break;
            }
        }
        String check = draggedView.getTag() != null ? draggedView.getTag().toString() : "UNKNOWN";
        ViewParent parent = draggedView.getParent();
        if (((ViewGroup) parent).getId() == R.id.positionOne && check.equals("AND")) {
            inputOne.setText("1");
        }
        if (((ViewGroup) parent).getId() == R.id.positionTwo && check.equals("AND")) {
            validateSecondInput = true;
        }
        if (((ViewGroup) parent).getId() == R.id.positionThree && check.equals("OR")) {
            inputThree.setText("1");
        }

        if (!droppedInTarget) {
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(draggedView);
            }

            String type = draggedView.getTag() != null ? draggedView.getTag().toString() : "UNKNOWN";

            if (type.equals("AND")) {
                if (((ViewGroup) parent).getId() == R.id.positionOne) {
                    updateGateCount(R.id.num_and, R.id.valueRight);
                } else {
                    updateGateCount(R.id.num_and, 0);
                }
            } else if (type.equals("OR")) {
                updateGateCount(R.id.num_or, R.id.finalValue);
            }
        }
        if (inputOne.getText().toString().equals("1") && inputThree.getText().toString().equals("1") && validateSecondInput) {
            ImageView lamp = findViewById(R.id.lamp);
            lamp.setImageResource(R.drawable.lamp_on);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.game, new FinishFragment())
                        .commit();
            }, 1000);
        }
    }

    private void updateGateCount(int textViewId, int textViewIdValue) {
        TextView replace = findViewById(textViewId);
        String text = replace.getText().toString();
        if (textViewIdValue != 0) {
            TextView inputOne = findViewById(textViewIdValue);
            inputOne.setText("0");
            ImageView lamp = findViewById(R.id.lamp);
            lamp.setImageResource(R.drawable.lampadina);
        }
        if (textViewId == 0) {
            ImageView lamp = findViewById(R.id.lamp);
            lamp.setImageResource(R.drawable.lampadina);
        }
        int num_gate = 0;
        if (text.contains("X")) {
            String[] parts = text.split("X");
            if (parts.length == 2) {
                try {
                    num_gate = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException ignored) {}
            }
        }

        num_gate++;

        if (text.contains("AND")) {
            replace.setText("AND X" + num_gate);
        } else if (text.contains("OR")) {
            replace.setText("OR X" + num_gate);
        } else {
            replace.setText("X" + num_gate);
        }
    }

    public void nextLevel(View v) {
        View rootView = findViewById(R.id.intro_overlay);
        if (rootView instanceof RelativeLayout) {
            RelativeLayout parentLayout = (RelativeLayout) rootView;

            FrameLayout overlay = new FrameLayout(getApplicationContext());
            overlay.setBackgroundResource(R.drawable.intro_text);
            TextView coomingSoon = new TextView(getApplicationContext());
            coomingSoon.setText("Coming Soon");
            coomingSoon.setTextColor(getResources().getColor(R.color.green_black));
            Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.vt323);
            coomingSoon.setTypeface(typeface);
            coomingSoon.setTextSize(30);
            FrameLayout.LayoutParams textParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            textParams.gravity = Gravity.CENTER;
            coomingSoon.setLayoutParams(textParams);
            overlay.addView(coomingSoon);
            ImageView closeIcon = new ImageView(getApplicationContext());
            closeIcon.setImageResource(R.drawable.ic_exit);

            int iconSize = 100;
            FrameLayout.LayoutParams closeParams = new FrameLayout.LayoutParams(
                    iconSize,
                    iconSize
            );
            closeParams.gravity = Gravity.END | Gravity.TOP;
            closeParams.setMargins(0, 16, 16, 0);
            closeIcon.setLayoutParams(closeParams);

            closeIcon.setOnClickListener(view -> parentLayout.removeView(overlay));
            overlay.addView(closeIcon);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, 250);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

            overlay.setLayoutParams(layoutParams);

            overlay.setClickable(true);
            parentLayout.addView(overlay);
        }
    }
    public void goBack(View view) {
        finish();
    }
}
