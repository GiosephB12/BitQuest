package com.example.bitquest;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

    private static SharedPreferences getPrefs(Context context, String email, String password) {
        if ("admin".equals(email) && "admin".equals(password)) {
            return context.getSharedPreferences("AdminPrefs", Context.MODE_PRIVATE);
        } else {
            return context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        }
    }

    public static int countUnlockedNotes(Context context, String email, String password) {
        SharedPreferences prefs = getPrefs(context, email, password);
        return prefs.getInt("unlocked_count", 0); // appunti sbloccati
    }

    public static int countUnlockedNotions(Context context, String email, String password) {
        SharedPreferences prefs = getPrefs(context, email, password);
        return prefs.getInt("unlocked_notions", 0); // nozioni sbloccate
    }

    public static int countUnlockedLevels(Context context, String email, String password) {
        SharedPreferences prefs = getPrefs(context, email, password);
        return prefs.getInt("unlocked_levels", 0); // livelli sbloccati
    }
}
