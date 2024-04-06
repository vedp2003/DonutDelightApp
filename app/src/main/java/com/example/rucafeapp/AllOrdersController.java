package com.example.rucafeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AllOrdersController extends AppCompatActivity {

    private Spinner orderNumberSpinner;
    private ListView allOrdersListView;
    private TextView orderTotalTextView;
    private Button cancelOrderButton;

    private OrderManager orderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);
        initializeUIComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupOrderNumberSpinner();
    }

    private void initializeUIComponents() {
        orderNumberSpinner = findViewById(R.id.orderNumber);
        allOrdersListView = findViewById(R.id.allOrders);
        orderTotalTextView = findViewById(R.id.totalAmount);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);

        orderManager = OrderManager.getInstance();

        Log.d("OrderDebugInstance3", "OrderManager instance hash: " + System.identityHashCode(orderManager));

        setupOrderNumberSpinner();

        cancelOrderButton.setOnClickListener(v -> cancelSelectedOrder());
    }

    private void setupOrderNumberSpinner() {
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, orderManager.getOrderNumbers());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderNumberSpinner.setAdapter(adapter);

        orderNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer orderNumber = (Integer) orderNumberSpinner.getSelectedItem();
                if (orderNumber != null) {
                    updateOrderDetails(orderNumber);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        if (!adapter.isEmpty()) {
            orderNumberSpinner.setSelection(0);
        } else {
            allOrdersListView.setAdapter(null);
            orderTotalTextView.setText(String.format("$%.2f", 0.0));
        }
    }

    private void updateOrderDetails(int orderNumber) {
        Order order = orderManager.getOrderFromIndex(orderNumber);
        if (order != null) {
            ArrayAdapter<MenuItem> orderItemsAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, order.getCurrentItems());
            allOrdersListView.setAdapter(orderItemsAdapter);
            orderTotalTextView.setText(String.format("$%.2f", order.getTotal()));
        }
    }

    private void cancelSelectedOrder() {
        Integer selectedOrderNumber = (Integer) orderNumberSpinner.getSelectedItem();
        if (selectedOrderNumber != null) {
            Order orderToCancel = orderManager.getOrderFromIndex(selectedOrderNumber);
            if (orderToCancel != null) {
                orderManager.removeOrder(orderToCancel);
                Toast.makeText(this, "Order cancelled successfully.", Toast.LENGTH_SHORT).show();
                setupOrderNumberSpinner();
            } else {
                Toast.makeText(this, "Please select an order to cancel.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
