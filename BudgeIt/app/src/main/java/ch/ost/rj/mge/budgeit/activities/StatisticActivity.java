package ch.ost.rj.mge.budgeit.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import ch.ost.rj.mge.budgeit.R;
import ch.ost.rj.mge.budgeit.model.Item;
import ch.ost.rj.mge.budgeit.services.ModelServices;
import ch.ost.rj.mge.budgeit.services.PreferencesService;

public class StatisticActivity extends AppCompatActivity {


    private Spinner categorySpinner;

    private BarChart barChart;
    private BarDataSet barDataSet;
    private BarData barData;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        // initialize menu
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_menu);
        bottomNav.setOnItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.statistic);

        // category spinner
        categorySpinner = findViewById(R.id.home_spinner_category);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateBarData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        List<String> categoryNames = ModelServices.getCategoryNamesForSpinner(getApplicationContext());
        categoryNames.add(0,"All Categories");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                categoryNames);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);


        // bar chart, need Spinner initialized!
        barChart = findViewById(R.id.settings_barchart);
        barChart.setTouchEnabled(true);
        barChart.setPinchZoom(true);
        barChart.getDescription().setText("");
        barChart.getDescription().setTextSize(12);
        barChart.getXAxis().setTextSize(12);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //barChart.getXAxis().setTextColor(0);
        updateBarData();

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateBarData() {
        PreferencesService service = new PreferencesService();
        String label = service.getCurrencySettingAsString(getApplicationContext());
        barDataSet = new BarDataSet(prepareBarData(), label);

        int color = ContextCompat.getColor(getApplicationContext(), R.color.primaryColor);
        barDataSet.setColor(color);

        barData = new BarData(barDataSet);
        barData.setBarWidth(1f);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.invalidate(); // refresh
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<BarEntry> prepareBarData() {
        String selectedCategory = categorySpinner.getSelectedItem().toString();

        List<Item> items=ModelServices.getNotDeleteItems(getApplicationContext(), selectedCategory);

        List<BarEntry> entries = new ArrayList<>();
        for (Item data : items) {
            // turn your data into Entry objects
            entries.add(new BarEntry(data.getDate().getDayOfMonth(), data.getAmount()));
        }
        return entries;
    }


    // navigation listener for menu
    private BottomNavigationView.OnItemSelectedListener navListener = item -> {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(HomeActivity.class);
                break;
            case R.id.settings:
                startActivity(SettingsActivity.class);
                break;
        }
        return true;
    };

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent (this, cls);
        startActivity(intent);
    }

}