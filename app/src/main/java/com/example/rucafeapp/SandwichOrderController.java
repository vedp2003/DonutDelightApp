package com.example.rucafeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SandwichOrderController extends AppCompatActivity {

    private ArrayList<MenuItem> sandwichOrders = new ArrayList<>();;
    private ArrayAdapter<MenuItem> sandwichCartAdapter;
    private double subtotalAmount;
    private int selectedSandwichIndex = -1;
    private View selectedView;
    private Order order;

    private Spinner sandwichQuantitySpinner;
    private Button addSandwichButton;
    private Button removeSandwichButton;
    private Button addToCartButton;
    private TextView sandwichSubtotal;
    private ListView sandwichCartListView;
    private RadioGroup breadsGroup;
    private RadioGroup proteinsGroup;
    private CheckBox lettuce;
    private CheckBox tomatoes;
    private CheckBox onions;
    private CheckBox cheese;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich_order);
        order = OrderManager.getInstance().getCurrentOrder();
        sandwichQuantitySpinner = findViewById(R.id.sandwichQuantity);
        addSandwichButton = findViewById(R.id.addSandwich);
        removeSandwichButton = findViewById(R.id.removeSandwich);
        addToCartButton = findViewById(R.id.sandwichAddToCartButton);
        sandwichSubtotal = findViewById(R.id.subtotalText);
        sandwichCartListView = findViewById(R.id.sandwichCart);
        breadsGroup = findViewById(R.id.breads);
        proteinsGroup = findViewById(R.id.proteins);
        lettuce = findViewById(R.id.lettuce);
        tomatoes = findViewById(R.id.tomatoes);
        onions = findViewById(R.id.onions);
        cheese = findViewById(R.id.cheese);
        sandwichSubtotal.setText(String.format("$%.2f", 0.0));
        ArrayList<String> quantities = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            quantities.add(String.valueOf(i));
        }
        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quantities);
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sandwichQuantitySpinner.setAdapter(quantityAdapter);
        sandwichCartAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sandwichOrders);
        sandwichCartListView.setAdapter(sandwichCartAdapter);
        addSandwichButton.setOnClickListener(v -> addSandwich());
        removeSandwichButton.setOnClickListener(v -> removeSandwich());
        addToCartButton.setOnClickListener(v -> addToCart());

        listViewItemListener();
    }

    private void listViewItemListener() {
        sandwichCartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(selectedView != null) {
                    selectedView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
                view.setBackgroundColor(getResources().getColor(R.color.highlight));
                selectedSandwichIndex = position;
                selectedView = view;
            }
        });
    }

    private void addToCart() {
        if (!sandwichOrders.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SandwichOrderController.this);
            builder.setMessage("Are you sure you want to add order to Current Orders Cart?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    order.addItemToCurrent(sandwichOrders);
                    sandwichOrders.clear();
                    sandwichCartAdapter.notifyDataSetChanged();
                    sandwichSubtotal.setText(String.format("$%.2f", 0.0));
                    sandwichQuantitySpinner.setSelection(0);

                    breadsGroup.clearCheck();
                    proteinsGroup.clearCheck();
                    lettuce.setChecked(false);
                    tomatoes.setChecked(false);
                    onions.setChecked(false);
                    cheese.setChecked(false);

                    Toast.makeText(SandwichOrderController.this,
                            "Your Sandwich Order Has Been Added to the Cart.", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(SandwichOrderController.this,
                    "ERROR: You must add a sandwich before placing the order.", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeSandwich() {
        if (selectedSandwichIndex >= 0) {
            Sandwich removedSandwich = (Sandwich) sandwichOrders.get(selectedSandwichIndex);
            sandwichOrders.remove(selectedSandwichIndex);
            sandwichCartAdapter.notifyDataSetChanged();

            subtotalAmount -= removedSandwich.price();
            if(subtotalAmount == -0.00){
                subtotalAmount = 0.0;
            }
            sandwichSubtotal.setText(String.format("$%.2f", subtotalAmount));

            if (selectedView != null) {
                selectedView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }

            selectedSandwichIndex = -1;
            selectedView = null;


        } else {
            Toast.makeText(SandwichOrderController.this,
                    "ERROR: Please select a sandwich to remove.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addSandwich() {
        int selectedId = breadsGroup.getCheckedRadioButtonId();
        int selectedId1 = proteinsGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(SandwichOrderController.this,
                    "ERROR: Please select a bread before adding sandwich to selected items.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedId1 == -1) {
            Toast.makeText(SandwichOrderController.this,
                    "ERROR: Please select a protein before adding sandwich to selected items.", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton selectedBread = findViewById(selectedId);
        String bread = selectedBread.getText().toString();
        RadioButton selectedProtein = findViewById(selectedId1);
        String protein = selectedProtein.getText().toString();
        ArrayList<String> addOns = new ArrayList<>();
        if (lettuce.isChecked()) addOns.add(Sandwich.LETTUCE_ADDON);
        if (tomatoes.isChecked()) addOns.add(Sandwich.TOMATOES_ADDON);
        if (onions.isChecked()) addOns.add(Sandwich.ONIONS_ADDON);
        if (cheese.isChecked()) addOns.add(Sandwich.CHEESE_ADDON);
        int quantity = Integer.parseInt(sandwichQuantitySpinner.getSelectedItem().toString());
        Sandwich newSandwichOrder = new Sandwich(bread, protein, addOns);
        newSandwichOrder.setQuantity(quantity);
        sandwichOrders.add(newSandwichOrder);
        subtotalAmount += newSandwichOrder.price();
        sandwichSubtotal.setText(String.format("$%.2f", subtotalAmount));
        sandwichQuantitySpinner.setSelection(0);
        breadsGroup.clearCheck();
        proteinsGroup.clearCheck();
        lettuce.setChecked(false);
        tomatoes.setChecked(false);
        onions.setChecked(false);
        cheese.setChecked(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}