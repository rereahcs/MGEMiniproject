package ch.ost.rj.mge.budgeit.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.util.List;

import ch.ost.rj.mge.budgeit.R;
import ch.ost.rj.mge.budgeit.db.BudgeItDatabase;
import ch.ost.rj.mge.budgeit.model.Category;
import ch.ost.rj.mge.budgeit.model.CategoryDao;
import ch.ost.rj.mge.budgeit.model.Item;
import ch.ost.rj.mge.budgeit.model.ItemDao;

public class ItemActivity extends AppCompatActivity {
    public static final String ITEMID = "";

    private TextInputEditText itemInputCategory;
    private TextInputEditText itemInputDescription;
    private TextInputEditText itemInputAmount;
    private TextInputEditText itemInputDate;

    private Button cancelButton;
    private Button deleteButton;
    private Button saveButton;

    private LocalDate now;

    boolean categoryPresent = false;
    boolean amountValid = false;
    boolean descriptionPresent = false;
    boolean saveButtonEnabled = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // initialize input fields
        itemInputCategory = findViewById(R.id.item_input_category);
        itemInputDescription = findViewById(R.id.item_input_description);
        itemInputAmount = findViewById(R.id.item_input_amount);
        itemInputDate = findViewById(R.id.item_input_date);

        // set date if new item is created
        now = LocalDate.now();
        String dateString = now.getDayOfMonth() + "." + now.getMonthValue() + "." + now.getYear();
        itemInputDate.setText(dateString);
        itemInputDate.setEnabled(false);

        // check category input
        itemInputCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                itemInputCategory.setError("Please enter a category");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    itemInputCategory.setError("Please enter a category");
                    categoryPresent = false;
                } else {
                    itemInputCategory.setError(null);
                    categoryPresent = true;
                }
                updateSaveButton();
            }
        });

        // check description input
        itemInputDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                itemInputDescription.setError("Please enter a description");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    itemInputDescription.setError("Please enter a description");
                    descriptionPresent = false;
                } else {
                    itemInputDescription.setError(null);
                    descriptionPresent = true;
                }
                updateSaveButton();
            }
        });

        // check amount input
        itemInputAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                itemInputAmount.setError("Please enter an amount");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    itemInputAmount.setError("Please enter an amount");
                    amountValid = false;
                } else {
                    itemInputAmount.setError(null);
                    amountValid = true;
                }
                updateSaveButton();
            }
        });

        // listener for cancel button
        cancelButton = findViewById(R.id.item_button_cancel);
        cancelButton.setOnClickListener(v -> startActivity(HomeActivity.class));

        // listener for delete button
        deleteButton = findViewById(R.id.item_button_delete);
        deleteButton.setOnClickListener(v -> {
            deleteEntry();
            Intent intent = new Intent(this, HomeActivity.class);
            // home activity should show snackbar
            intent.putExtra("showSnackbar", true);
            startActivity(intent);
        });

        // listener for save button
        saveButton = findViewById(R.id.item_button_save);
        saveButton.setOnClickListener(v -> {
            saveEntry();
            startActivity(HomeActivity.class);
        });

        // initialize save button availability
        updateSaveButton();
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent (this, cls);
        startActivity(intent);
    }

    private void deleteEntry() {
        // TODO: delete entry
    }

    private void saveEntry() {
        // get information on the item
        String itemCategory = itemInputCategory.getText().toString();
        String itemDescription = itemInputDescription.getText().toString();
        String itemAmount = itemInputAmount.getText().toString();
        float itemAmountFloat;
        try {
            itemAmountFloat = Float.parseFloat(itemAmount);
        } catch (NumberFormatException e) {
            itemInputAmount.setError("Please enter a valid amount");
            amountValid = false;
            updateSaveButton();
            return;
        }

        // TODO: case update
        // save new item to db
        Item item = new Item(itemCategory, itemDescription, itemAmountFloat, now);
        BudgeItDatabase db = BudgeItDatabase.getInstance(getApplicationContext());
        ItemDao itemDao = db.itemDao();
        itemDao.insert(item);

        // add category to db if it does not exist yet
        CategoryDao categoryDao = db.categoryDao();
        List<Category> categoryList = categoryDao.getCategories();
        boolean newCategory = true;
        for (Category category : categoryList) {
            if (category.getName().equals(itemCategory)) {
                newCategory = false;
                break;
            }
        }
        if (newCategory) {
            categoryDao.insert(new Category(itemCategory));
        }

        // create toast
        String text = "Entry saved successfully";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    private void updateSaveButton() {
        saveButtonEnabled = categoryPresent && amountValid;
        float saveButtonAlpha = saveButtonEnabled ? 1.0f : 0.5f;

        saveButton.setEnabled(saveButtonEnabled);
        saveButton.setAlpha(saveButtonAlpha);
    }
}