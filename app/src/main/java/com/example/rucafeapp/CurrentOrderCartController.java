package com.example.rucafeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CurrentOrderCartController extends AppCompatActivity {

    private ListView currentOrdersCartListView;
    private TextView currentSubtotalTextView, currentTaxTextView, currentTotalTextView;
    private Button removeItemButton, placeOrderButton;
    private Order order;
    private OrderManager orderManager;
    private ArrayAdapter<MenuItem> adapter;
    private int selectedCoffeeIndex = -1;
    private View selectedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_cart);

        currentOrdersCartListView = findViewById(R.id.currentOrdersCart);
        currentSubtotalTextView = findViewById(R.id.subTotal);
        currentTaxTextView = findViewById(R.id.salesTax);
        currentTotalTextView = findViewById(R.id.totalAmount);
        removeItemButton = findViewById(R.id.removeItemButton);
        placeOrderButton = findViewById(R.id.placeOrderButton);
        currentSubtotalTextView.setText(String.format("$%.2f", 0.0));
        currentTaxTextView.setText(String.format("$%.2f", 0.0));
        currentTotalTextView.setText(String.format("$%.2f", 0.0));

        this.orderManager = OrderManager.getInstance();
        order = orderManager.getCurrentOrder();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, order.getCurrentItems());
        currentOrdersCartListView.setAdapter(adapter);
        currentOrdersCartListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        updateOrderDetails();

        currentOrdersCartListView.setOnItemClickListener((parent, view, position, id) -> {
            if(selectedView != null) {
                selectedView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
            view.setBackgroundColor(getResources().getColor(R.color.highlight));
            selectedCoffeeIndex = position;
            selectedView = view;
        });

        removeItemButton.setOnClickListener(v -> removeSelectedItem());
        placeOrderButton.setOnClickListener(v -> placeOrder());
    }

    private void updateOrderDetails() {
        currentSubtotalTextView.setText(String.format("$%.2f", order.getSubTotal()));
        currentTaxTextView.setText(String.format("$%.2f", order.getSalesTax()));
        currentTotalTextView.setText(String.format("$%.2f", order.getTotal()));
    }

    private void removeSelectedItem() {
        if (selectedCoffeeIndex >= 0) {
            MenuItem selectedItem = adapter.getItem(selectedCoffeeIndex);
            order.removeItem(selectedItem);
            adapter.notifyDataSetChanged();
            updateOrderDetails();
            Toast.makeText(this, "Item removed successfully", Toast.LENGTH_SHORT).show();

            if (selectedView != null) {
                selectedView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
            selectedCoffeeIndex = -1;
            selectedView = null;
        } else {
            Toast.makeText(this, "Please select an item to remove.", Toast.LENGTH_SHORT).show();
        }
    }

    private void placeOrder() {
        if (!adapter.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to place the order?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    orderManager.addOrder();

                    adapter.clear();
                    currentSubtotalTextView.setText(String.format("$%.2f", 0.0));
                    currentTaxTextView.setText(String.format("$%.2f", 0.0));
                    currentTotalTextView.setText(String.format("$%.2f", 0.0));

                    Toast.makeText(getApplicationContext(),
                            "Your order has been placed successfully.", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            Toast.makeText(this,
                    "Your current cart is empty. Order items before placing the order.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}