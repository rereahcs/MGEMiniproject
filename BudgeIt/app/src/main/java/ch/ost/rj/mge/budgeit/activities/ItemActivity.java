package ch.ost.rj.mge.budgeit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import ch.ost.rj.mge.budgeit.R;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // listener for cancel button
        Button cancelButton = findViewById(R.id.item_button_cancel);
        cancelButton.setOnClickListener(v -> startActivity(HomeActivity.class));

        // listener for delete button
        Button deleteButton = findViewById(R.id.item_button_delete);
        deleteButton.setOnClickListener(v -> {
            deleteEntry();
            Intent intent = new Intent(this, HomeActivity.class);
            // home activity should show snackbar
            intent.putExtra("showSnackbar", true);
            startActivity(intent);
        });

        // listener for save button
        Button saveButton = findViewById(R.id.item_button_save);
        saveButton.setOnClickListener(v -> {
            saveEntry();
            startActivity(HomeActivity.class);
        });


    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent (this, cls);
        startActivity(intent);
    }

    private void deleteEntry() {
        // TODO: delete entry
    }

    private void saveEntry() {
        // TODO: save entry
        // toast
        String text = "Entry saved successfully";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }
}