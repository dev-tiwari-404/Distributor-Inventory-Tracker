package com.proj.smartdistributor;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editItemName, editQuantity;
    private TextView textStatus;
    private InventoryDbHelper dbHelper;

    // Low stock threshold
    private static final int LOW_STOCK_ALERT_LEVEL = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editItemName = findViewById(R.id.editItemName);
        editQuantity = findViewById(R.id.editQuantity);
        textStatus = findViewById(R.id.textStatus);
        Button btnInbound = findViewById(R.id.btnInbound);
        Button btnOutbound = findViewById(R.id.btnOutbound);

        dbHelper = new InventoryDbHelper(this);

        btnInbound.setOnClickListener(v -> processTransaction(true));
        btnOutbound.setOnClickListener(v -> processTransaction(false));
    }

    private void processTransaction(boolean isInbound) {
        String itemName = editItemName.getText().toString().trim();
        String qtyString = editQuantity.getText().toString().trim();

        if (itemName.isEmpty() || qtyString.isEmpty()) {
            Toast.makeText(this, "Please enter item and quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = Integer.parseInt(qtyString);

        // If outbound, we subtract from the database
        int modifier = isInbound ? quantity : -quantity;

        // Update database
        dbHelper.updateStock(itemName, modifier);

        // Fetch new stock level to display
        int currentStock = dbHelper.getStock(itemName);

        updateUI(itemName, currentStock);

        // Clear inputs for the next entry
        editItemName.setText("");
        editQuantity.setText("");
    }

    private void updateUI(String itemName, int currentStock) {
        String statusMessage = itemName + " Current Stock: " + currentStock;

        // Low Stock Alert Logic
        if (currentStock < LOW_STOCK_ALERT_LEVEL) {
            statusMessage += "\n⚠️ LOW STOCK ALERT!";
            textStatus.setTextColor(Color.RED);
        } else {
            textStatus.setTextColor(Color.DKGRAY);
        }

        textStatus.setText(statusMessage);
    }
}