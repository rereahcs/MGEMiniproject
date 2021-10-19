package ch.ost.rj.mge.budgeit.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import ch.ost.rj.mge.budgeit.R;
import ch.ost.rj.mge.budgeit.model.Item;
import ch.ost.rj.mge.budgeit.services.ModelServices;

public class StatisticActivity extends AppCompatActivity {


    private Spinner intervalSpinner;
    //private ModelServices modelServices;
    private LineChart lineChart;
    private LineDataSet lineDataSet;
    private LineData lineData;
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
        intervalSpinner = findViewById(R.id.home_spinner_category);
        intervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateLineData();
                updateBarData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                updateLineData();
                updateBarData();
            }

        });
        List<String> categoryNames = ModelServices.getCategoryNamesForSpinner(getApplicationContext());
        categoryNames.add(0,"All");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                categoryNames);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(categoryAdapter);

        // line chart
        lineChart = findViewById(R.id.settings_linechart);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.getDescription().setText("Ausgaben im Monat");
        lineChart.getDescription().setTextSize(12);
        lineChart.getXAxis().setTextColor(0);
        updateLineData();

        // bar chart

        barChart = findViewById(R.id.settings_barchart);
        barChart.setTouchEnabled(true);
        barChart.setPinchZoom(true);
        barChart.getDescription().setText("Ausgaben im Monat");
        barChart.getDescription().setTextSize(12);
        barChart.getXAxis().setTextColor(0);
        updateBarData();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateLineData() {
        lineDataSet = new LineDataSet(prepareLineData(), "Label"); // add entries to dataset
        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateBarData() {
        barDataSet = new BarDataSet(prepareBarData(), "Label");
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.invalidate(); // refresh
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Entry> prepareLineData() {
        String selectedCategory = intervalSpinner.getSelectedItem().toString();
        List<Item> items;
        if(selectedCategory=="All") {
            items = ModelServices.getItemsByCategory(getApplicationContext(), selectedCategory);
        } else {
            items = ModelServices.getAllItems(getApplicationContext());
        }

        List<Entry> entries = new ArrayList<>();
        for (Item data : items) {
            // turn your data into Entry objects
            entries.add(new Entry(data.getDate().getDayOfMonth(), data.getAmount()));
        }
        return entries;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<BarEntry> prepareBarData() {

        List<Item> items = ModelServices.getAllItems(getApplicationContext());
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