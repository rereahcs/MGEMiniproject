package ch.ost.rj.mge.budgeit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ch.ost.rj.mge.budgeit.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // initialize menu
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_menu);
        bottomNav.setOnItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.settings);

        // interval spinner
        Spinner intervalSpinner = findViewById(R.id.preferences_spinner_interval);
        ArrayAdapter<CharSequence> intervalAdapter = ArrayAdapter.createFromResource(this,
                R.array.interval_array, android.R.layout.simple_spinner_item);
        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(intervalAdapter);

        // currency spinner
        Spinner currencySpinner = findViewById(R.id.preferences_spinner_currency);
        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(currencyAdapter);
    }

    // navigation listener for menu
    public BottomNavigationView.OnItemSelectedListener navListener = item -> {
        switch (item.getItemId()) {
            case R.id.statistic:
                startActivity(StatisticActivity.class);
                break;
            case R.id.home:
                startActivity(HomeActivity.class);
                break;
        }
        return true;
    };

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent (this, cls);
        startActivity(intent);
    }
}