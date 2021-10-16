package ch.ost.rj.mge.budgeit.services;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesService {
    static String file = "ch.ost.rj.mge.budgeit.preferences";
    static String amountKey = "key.amount";
    static String intervalKey = "key.interval";
    static String currencyKey = "key.currency";
    static int mode = Context.MODE_PRIVATE;

    // amount settings
    public void writeAmountSetting(Context context, int amount) {
        SharedPreferences preferences = getSharedPreferencesObject(context);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(amountKey, amount);
        editor.commit();
    }

    public int readAmountSetting(Context context) {
        SharedPreferences preferences = getSharedPreferencesObject(context);

        return preferences.getInt(amountKey, 1000);
    }

    // interval settings
    public void writeIntervalSetting(Context context, int intervalIndex) {
        SharedPreferences preferences = getSharedPreferencesObject(context);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(intervalKey, intervalIndex);
        editor.commit();
    }

    public int readIntervalSetting(Context context) {
        SharedPreferences preferences = getSharedPreferencesObject(context);

        return preferences.getInt(intervalKey, 0);
    }

    // currency settings
    public void writeCurrencySetting(Context context, int currencyIndex) {
        SharedPreferences preferences = getSharedPreferencesObject(context);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(currencyKey, currencyIndex);
        editor.commit();
    }

    public int readCurrencySetting(Context context) {
        SharedPreferences preferences = getSharedPreferencesObject(context);

        return preferences.getInt(currencyKey, 0);
    }

    // helper method
    private SharedPreferences getSharedPreferencesObject(Context context) {
        return context.getSharedPreferences(file, mode);
    }

}


