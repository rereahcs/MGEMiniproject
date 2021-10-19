package ch.ost.rj.mge.budgeit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import ch.ost.rj.mge.budgeit.R;
import ch.ost.rj.mge.budgeit.adapter.ItemViewHolder;
import ch.ost.rj.mge.budgeit.adapter.ItemsAdapter;
import ch.ost.rj.mge.budgeit.adapter.OnItemClickListenerHome;
import ch.ost.rj.mge.budgeit.db.BudgeItDatabase;
import ch.ost.rj.mge.budgeit.model.Item;
import ch.ost.rj.mge.budgeit.model.ItemDao;

public class HomeActivity extends AppCompatActivity implements OnItemClickListenerHome {

    RecyclerView.Adapter<ItemViewHolder> adapter;
    private List<Item> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // initialize menu
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_menu);
        bottomNav.setOnItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.home);

        // listener for add button
        FloatingActionButton addButton = findViewById(R.id.home_floatingbutton_additem);
        addButton.setOnClickListener(v -> startActivity(ItemActivity.class));

        // check if snackbar should be showed (from ItemActivity delete entry)
        boolean showSnackbar = getIntent().getBooleanExtra("showSnackbar", false);
        if (showSnackbar) {
            showSnackbar();
        }

        data = getAllItems();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ItemsAdapter(data, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        RecyclerView recyclerView = findViewById(R.id.home_recyclerView_items);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);




    }

    // navigation listener for menu
    private BottomNavigationView.OnItemSelectedListener navListener = item -> {
        switch (item.getItemId()) {
            case R.id.statistic:
                startActivity(StatisticActivity.class);
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

    private void showSnackbar() {
        ViewGroup parent = findViewById(R.id.layout_activity_home);
        String text = "Entry deleted";
        int duration = Snackbar.LENGTH_LONG;
        String action = "Undo";

        Snackbar snackbar = Snackbar.make(parent, text, duration);
        // TODO: implement undo --> idea: only delete logically (flag) and use id to set the flag to false again
        snackbar.setAction(action, v -> snackbar.dismiss());
        snackbar.show();
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

    @Override
    public void onItemClick(int position) {
        Item item = data.get(position);
        Intent intent = new Intent(this,ItemActivity.class );
        startActivity(intent);
    }
}