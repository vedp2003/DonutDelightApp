package com.example.rucafeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DonutOrderController extends AppCompatActivity {

    private RecyclerView donutRecyclerView;
    private TextView subtotalTextView;
    private Order order;
    private double subtotal = 0.0;
    private ArrayList<MenuItem> selectedDonuts = new ArrayList<>();
    private DonutAdapter donutAdapter;

    private ListView selectedDonutsListView;
    private ArrayAdapter<MenuItem> selectedDonutsAdapter;

    private int selectedDonutIndex = -1;
    private View selectedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut_order);

        selectedDonutsListView = findViewById(R.id.selectedDonutsListView);
        selectedDonutsAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                selectedDonuts
        );
        selectedDonutsListView.setAdapter(selectedDonutsAdapter);

        order = OrderManager.getInstance().getCurrentOrder();
        donutRecyclerView = findViewById(R.id.donutRecyclerView);
        subtotalTextView = findViewById(R.id.subtotalTextView);

        ArrayList<MenuItem> donuts = initializeDonuts();
        int[] imageResources = initializeDonutImages();

        donutAdapter = new DonutAdapter(donuts, this, imageResources);
        donutRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        donutRecyclerView.setAdapter(donutAdapter);



        selectedDonutsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(selectedView != null) {
                    selectedView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
                view.setBackgroundColor(getResources().getColor(R.color.highlight));
                selectedDonutIndex = position;
                selectedView = view;
            }
        });
    }

    public void addDonutToOrder(Donut donut, double price) {
        selectedDonuts.add(donut);
        selectedDonutsAdapter.notifyDataSetChanged();
        subtotal += price;
        subtotalTextView.setText(String.format("Subtotal: $%.2f", subtotal));
    }

    public void onAddToCartClicked(View view) {

        if (!selectedDonuts.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to add order to Current Orders Cart?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    order.addItemToCurrent(selectedDonuts);
                    selectedDonuts.clear();
                    subtotal = 0.0;
                    subtotalTextView.setText(String.format("Subtotal: $%.2f", subtotal));
                    donutAdapter.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), "Your Donuts Order Has Been Added to the Cart.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Please select donuts to add to the cart", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRemoveSelectedItemClicked(View view) {

        if (selectedDonutIndex >= 0) {
            Donut removedDonut = (Donut) selectedDonuts.get(selectedDonutIndex);
            selectedDonuts.remove(selectedDonutIndex);

            selectedDonutsAdapter.notifyDataSetChanged();

            subtotal -= removedDonut.price();
            if(subtotal == -0.00){
                subtotal = 0.0;
            }
            subtotalTextView.setText(String.format("Subtotal: $%.2f", subtotal));

            if (selectedView != null) {
                selectedView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }

            selectedDonutIndex = -1;
            selectedView = null;


        } else {
            Toast.makeText(DonutOrderController.this, "ERROR: Please select a donut to remove.", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<MenuItem> initializeDonuts() {
        ArrayList<MenuItem> donuts = new ArrayList<>();

        donuts.add(new Donut("Yeast Donuts", "Chocolate-Frosted"));
        donuts.add(new Donut("Yeast Donuts", "Boston Creme"));
        donuts.add(new Donut("Yeast Donuts", "Cinnamon Sugar"));
        donuts.add(new Donut("Yeast Donuts", "Jelly-Filled"));
        donuts.add(new Donut("Yeast Donuts", "Maple Frosted"));
        donuts.add(new Donut("Yeast Donuts", "Vanilla"));

        donuts.add(new Donut("Cake Donuts", "Raspberry"));
        donuts.add(new Donut("Cake Donuts", "Apple"));
        donuts.add(new Donut("Cake Donuts", "Blueberry"));

        donuts.add(new Donut("Donut Holes", "Powdered"));
        donuts.add(new Donut("Donut Holes", "Cinnamon Twist"));
        donuts.add(new Donut("Donut Holes", "Classic Glazed"));

        return donuts;
    }

    private int[] initializeDonutImages() {
        return new int[] {
                R.drawable.yeast_donut_a,
                R.drawable.yeast_donut_b,
                R.drawable.yeast_donut_c,
                R.drawable.yeast_donut_d,
                R.drawable.yeast_donut_e,
                R.drawable.yeast_donut_f,
                R.drawable.cake_donut_a,
                R.drawable.cake_donut_b,
                R.drawable.cake_donut_c,
                R.drawable.donut_holes_a,
                R.drawable.donut_holes_b,
                R.drawable.donut_holes_c
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}