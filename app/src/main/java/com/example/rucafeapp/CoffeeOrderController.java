package com.example.rucafeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CoffeeOrderController extends AppCompatActivity {

    private ArrayList<MenuItem> coffeeOrders;
    private ArrayAdapter<MenuItem> coffeeCartAdapter;
    private double subtotalAmount;
    private int selectedCoffeeIndex = -1;
    private View selectedView;
    private Order order;


    private Spinner coffeeQuantitySpinner;
    private Button addCoffeeButton;
    private Button removeCoffeeButton;
    private Button addToCartButton;
    private TextView coffeeSubtotal;
    private ListView coffeeCartListView;
    private RadioGroup coffeeCupSizeToggleGroup;
    private CheckBox sweetCream;
    private CheckBox frenchVanilla;
    private CheckBox irishCream;
    private CheckBox caramel;
    private CheckBox mocha;


    public CoffeeOrderController() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_order);
        order = OrderManager.getInstance().getCurrentOrder();
        coffeeQuantitySpinner = findViewById(R.id.coffeeQuantity);
        addCoffeeButton = findViewById(R.id.addCoffee);
        removeCoffeeButton = findViewById(R.id.removeCoffee);
        addToCartButton = findViewById(R.id.coffeeAddToCartButton);
        coffeeSubtotal = findViewById(R.id.subtotalText);
        coffeeCartListView = findViewById(R.id.coffeeCart);
        coffeeCupSizeToggleGroup = findViewById(R.id.cupSizes);
        sweetCream = findViewById(R.id.sweetCream);
        frenchVanilla = findViewById(R.id.frenchVanilla);
        irishCream = findViewById(R.id.irishCream);
        caramel = findViewById(R.id.caramel);
        mocha = findViewById(R.id.mocha);

        coffeeSubtotal.setText("0.0");

        ArrayList<String> quantities = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            quantities.add(String.valueOf(i));
        }
        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quantities);
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coffeeQuantitySpinner.setAdapter(quantityAdapter);

        coffeeOrders = new ArrayList<>();
        coffeeCartAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, coffeeOrders);
        coffeeCartListView.setAdapter(coffeeCartAdapter);


        addCoffeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = coffeeCupSizeToggleGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(CoffeeOrderController.this, "ERROR: Please select a coffee cup size before adding coffee to selected items.", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton selectedSize = findViewById(selectedId);
                String cupSize = selectedSize.getText().toString();

                ArrayList<String> addIns = new ArrayList<>();
                if (sweetCream.isChecked()) addIns.add(sweetCream.getText().toString());
                if (frenchVanilla.isChecked()) addIns.add(frenchVanilla.getText().toString());
                if (irishCream.isChecked()) addIns.add(irishCream.getText().toString());
                if (caramel.isChecked()) addIns.add(caramel.getText().toString());
                if (mocha.isChecked()) addIns.add(mocha.getText().toString());

                int quantity = Integer.parseInt(coffeeQuantitySpinner.getSelectedItem().toString());

                Coffee newCoffeeOrder = new Coffee(cupSize, addIns);
                newCoffeeOrder.setQuantity(quantity);

                coffeeOrders.add(newCoffeeOrder);

                subtotalAmount += newCoffeeOrder.price();
                coffeeSubtotal.setText(String.format("%.2f", subtotalAmount));

                coffeeQuantitySpinner.setSelection(0);
                coffeeCupSizeToggleGroup.clearCheck();
                sweetCream.setChecked(false);
                frenchVanilla.setChecked(false);
                irishCream.setChecked(false);
                caramel.setChecked(false);
                mocha.setChecked(false);
            }
        });

        coffeeCartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(selectedView != null) {
                    selectedView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
                view.setBackgroundColor(getResources().getColor(R.color.selected_item));
                selectedCoffeeIndex = position;
                selectedView = view;
            }
        });


        removeCoffeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCoffeeIndex >= 0) {
                    Coffee removedCoffee = (Coffee) coffeeOrders.get(selectedCoffeeIndex);
                    coffeeOrders.remove(selectedCoffeeIndex);
                    coffeeCartAdapter.notifyDataSetChanged();

                    subtotalAmount -= removedCoffee.price();
                    if(subtotalAmount == -0.00){
                        subtotalAmount = 0.0;
                    }
                    coffeeSubtotal.setText(String.format("%.2f", subtotalAmount));

                    if (selectedView != null) {
                        selectedView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    }

                    selectedCoffeeIndex = -1;
                    selectedView = null;


                } else {
                    Toast.makeText(CoffeeOrderController.this, "ERROR: Please select a coffee to remove.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!coffeeOrders.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CoffeeOrderController.this);
                    builder.setMessage("Are you sure you want to add order to Current Orders Cart?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            order.addItemToCurrent(coffeeOrders);
                            coffeeOrders.clear();
                            coffeeCartAdapter.notifyDataSetChanged();
                            coffeeSubtotal.setText(String.format("%.2f", 0.0));
                            coffeeQuantitySpinner.setSelection(0);

                            coffeeCupSizeToggleGroup.clearCheck();
                            sweetCream.setChecked(false);
                            frenchVanilla.setChecked(false);
                            irishCream.setChecked(false);
                            caramel.setChecked(false);
                            mocha.setChecked(false);

                            Toast.makeText(CoffeeOrderController.this, "Your Coffee Order Has Been Added to the Cart.", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(CoffeeOrderController.this, "ERROR: You must add coffee before placing the order.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

}
