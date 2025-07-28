package com.example.bitquest;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

public class Utils {

    public static int countUnlockedNotes(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return prefs.getInt("unlocked_count", 0); // appunti sbloccati salvati da AppuntiPage
    }

    public static int countUnlockedNotions(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return prefs.getInt("unlocked_notions", 0); // nozioni sbloccate salvate da ArchivioPage
    }

    public static int countUnlockedLevels(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return prefs.getInt("unlocked_levels", 0); // gestisci similmente il salvataggio livelli da implementare
    }
}
