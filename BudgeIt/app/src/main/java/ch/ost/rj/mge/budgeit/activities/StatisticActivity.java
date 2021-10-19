package ch.ost.rj.mge.budgeit.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

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
import ch.ost.rj.mge.budgeit.db.BudgeItDatabase;
import ch.ost.rj.mge.budgeit.model.Item;
import ch.ost.rj.mge.budgeit.model.ItemDao;

public class StatisticActivity extends AppCompatActivity {

    private LineChart chart;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        // initialize menu
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_menu);
        bottomNav.setOnItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.statistic);

        chart = (LineChart) findViewById(R.id.settings_linechart);
        chart.setTouchEnabled(true);
        chart.setPinchZoom(true);
        LineDataSet dataSet = new LineDataSet(prepareLineData(), "Label"); // add entries to dataset
//        dataSet.setColor(...);
//        dataSet.setValueTextColor(...);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.getDescription().setText("Ausgaben im Monat");
        chart.getDescription().setTextSize(12);
        chart.getXAxis().setTextColor(0);
        chart.invalidate(); // refresh

        BarDataSet dataSetBarChart = new BarDataSet(prepareBarData(), "Label");
        BarChart bchart = (BarChart) findViewById(R.id.settings_barchart);
        bchart.setTouchEnabled(true);
        bchart.setPinchZoom(true);
        BarData barData = new BarData(dataSetBarChart);
        bchart.setData(barData);
        bchart.getDescription().setText("Ausgaben im Monat");
        bchart.getDescription().setTextSize(12);
        bchart.getXAxis().setTextColor(0);
        bchart.invalidate(); // refresh
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Entry> prepareLineData() {

        List<Item> items = getAllItems();
        List<Entry> entries = new ArrayList<>();
        for (Item data : items) {
            // turn your data into Entry objects
            entries.add(new Entry(data.getDate().getDayOfMonth(), data.getAmount()));
        }
        return entries;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<BarEntry> prepareBarData() {

        List<Item> items = getAllItems();
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

    // db-helper methods for adapter
    private List<Item> getAllItems() {
        BudgeItDatabase db = BudgeItDatabase.getInstance(getApplicationContext());
        ItemDao itemDao = db.itemDao();
        return itemDao.getItems();
    }

    // db-helper methods for adapter
    private List<Item> getItemsByCategory(String categoryName) {
        BudgeItDatabase db = BudgeItDatabase.getInstance(getApplicationContext());
        ItemDao itemDao = db.itemDao();
        return itemDao.getItemsByCategory(categoryName);
    }
}