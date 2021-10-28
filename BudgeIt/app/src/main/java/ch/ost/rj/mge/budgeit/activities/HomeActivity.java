package ch.ost.rj.mge.budgeit.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import ch.ost.rj.mge.budgeit.R;
import ch.ost.rj.mge.budgeit.adapter.ItemViewHolder;
import ch.ost.rj.mge.budgeit.adapter.ItemsAdapter;
import ch.ost.rj.mge.budgeit.adapter.OnItemClickListenerHome;
import ch.ost.rj.mge.budgeit.model.Item;
import ch.ost.rj.mge.budgeit.services.ModelServices;
import ch.ost.rj.mge.budgeit.services.PreferencesService;

public class HomeActivity extends AppCompatActivity implements OnItemClickListenerHome {

    RecyclerView.Adapter<ItemViewHolder> adapter;
    private List<Item> data;
    private TextView budget;
    private Spinner categorySpinner;
    private TextView emptyView;
    private RecyclerView recyclerView;
    private ImageView imageView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // initialize menu
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_menu);
        bottomNav.setOnItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.home);

        // category spinner
        categorySpinner = findViewById(R.id.home_spinner_category);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateItems();
                updateAdapter();
                updateRestBudget();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        List<String> categoryNames = ModelServices.getCategoryNamesForSpinner(getApplicationContext());
        categoryNames.add(0,"All Categories");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categoryNames);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        //RestBudget Textview
        budget = findViewById(R.id.home_text_restbudget);
        updateRestBudget();

        //Currency Textview
        TextView currencyTextView = findViewById(R.id.home_text_currency);
        PreferencesService service = new PreferencesService();
        String currencyString = service.getCurrencySettingAsString(this);
        String totalAmount = String.valueOf(service.readAmountSetting(this));
        String currencyText = "von " + totalAmount + " " + currencyString;
        currencyTextView.setText(currencyText);

        // listener for add button
        FloatingActionButton addButton = findViewById(R.id.home_floatingbutton_additem);
        addButton.setOnClickListener(v -> startActivity(ItemActivity.class));

        // check if snackbar should be showed (from ItemActivity delete entry)
        boolean showSnackbar = getIntent().getBooleanExtra("showSnackbar", false);
        if (showSnackbar) {
            int deletedItemId = getIntent().getIntExtra("deletedItemId", -1);
            showSnackbar(deletedItemId);
        }

        // adapter and no Items TextView
        emptyView = (TextView) findViewById(R.id.home_textview_noitems);
        recyclerView = findViewById(R.id.home_recyclerView_items);
        imageView = findViewById(R.id.home_imageView_icon);
        updateAdapter();

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateItems() {
        String selectedCategory = categorySpinner.getSelectedItem().toString();
        data = ModelServices.getNotDeleteItems(getApplicationContext(), selectedCategory);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateAdapter() {
        updateItems();
        if(data.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
        } else {
            reloadAdapter();
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
        }
    }

    private void reloadAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ItemsAdapter(data, this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getApplicationContext().getResources().getDrawable(R.drawable.itemlist_divider));

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateRestBudget() {
        String selectedCategory = categorySpinner.getSelectedItem().toString();
        float budgetFloat = ModelServices.getRestBudget(getApplicationContext(), selectedCategory);
        budget.setText(String.valueOf(budgetFloat));
    }

    // navigation listener for menu
    private final BottomNavigationView.OnItemSelectedListener navListener = item -> {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showSnackbar(int deletedItemId) {
        ViewGroup parent = findViewById(R.id.layout_activity_home);
        String text = "Entry deleted";
        int duration = Snackbar.LENGTH_LONG;
        String action = "Undo";

        Snackbar snackbar = Snackbar.make(parent, text, duration);

        // undo deletion of item
        snackbar.setAction(action, v -> {
            Item item = ModelServices.getItemById(getApplicationContext(), deletedItemId);
            item.setDeleted(false);
            ModelServices.updateItem(getApplicationContext(), item);
            updateRestBudget();
            updateAdapter();
            snackbar.dismiss();
        });

        snackbar.show();
    }

    @Override
    public void onItemClick(int position) {
        Item item = data.get(position);
        Intent intent = new Intent(this, ItemActivity.class );
        intent.putExtra("itemId", item.getId());
        startActivity(intent);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            Item item = data.get(viewHolder.getAdapterPosition());
            item.setDeleted(true);
            ModelServices.updateItem(getApplicationContext(), item);

            showSnackbar(item.getId());
            updateRestBudget();
            updateAdapter();

        }
    };
}