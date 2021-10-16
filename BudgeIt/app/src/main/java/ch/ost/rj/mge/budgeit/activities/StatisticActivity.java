package ch.ost.rj.mge.budgeit.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.ost.rj.mge.budgeit.R;
import ch.ost.rj.mge.budgeit.model.Item;

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

        chart = (LineChart) findViewById(R.id.settings_barchart);
        List<Entry> entries = genereateTestData();

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
//        dataSet.setColor(...);
//        dataSet.setValueTextColor(...);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Entry> genereateTestData() {
        Item[] dataObjects= new Item[4];
        dataObjects[0] = new Item("lunch", "", 16.50F, LocalDate.now());
        dataObjects[1] = new Item("bread", "", 3.50F, LocalDate.now());
        dataObjects[2] = new Item("weekly shopping", "", 40.00F, LocalDate.now());
        dataObjects[3] = new Item("lunch", "", 2.50F, LocalDate.now());

        List<Entry> entries = new ArrayList<Entry>();
        for (Item data : dataObjects) {
            // turn your data into Entry objects
            entries.add(new Entry(data.getAmount(), data.getAmount()));
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