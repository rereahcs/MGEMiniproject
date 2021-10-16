package ch.ost.rj.mge.budgeit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import ch.ost.rj.mge.budgeit.R;
import ch.ost.rj.mge.budgeit.services.PreferencesService;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        PreferencesService preferencesService = new PreferencesService();
        Context context = this;

        // initialize menu
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_menu);
        bottomNav.setOnItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.settings);

        // amount input
        TextInputEditText amountInput = findViewById(R.id.preferences_input_amount);
        amountInput.setText(Integer.toString(preferencesService.readAmountSetting(context)));
        amountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    preferencesService.writeAmountSetting(context, Integer.parseInt(amountInput.getText().toString()));
                } catch (NumberFormatException e) {
                    amountInput.setError("Please provide a number");
                }
            }
        });

        // interval spinner
        Spinner intervalSpinner = findViewById(R.id.preferences_spinner_interval);
        ArrayAdapter<CharSequence> intervalAdapter = ArrayAdapter.createFromResource(this,
                R.array.interval_array, android.R.layout.simple_spinner_item);
        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(intervalAdapter);

        // set saved interval selection and save changed interval selection
        int currentIntervalSelection = preferencesService.readIntervalSetting(context);
        intervalSpinner.setSelection(currentIntervalSelection);
        intervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (currentIntervalSelection != position) {
                    preferencesService.writeIntervalSetting(context, position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        // currency spinner
        Spinner currencySpinner = findViewById(R.id.preferences_spinner_currency);
        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(currencyAdapter);

        // set saved currency selection and save changed currency selection
        int currentCurrencySelection = preferencesService.readCurrencySetting(context);
        currencySpinner.setSelection(currentCurrencySelection);
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (currentCurrencySelection != position) {
                    preferencesService.writeCurrencySetting(context, position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    // navigation listener for menu
    private BottomNavigationView.OnItemSelectedListener navListener = item -> {
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