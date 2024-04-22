package com.example.rucafeapp;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orderingImageButtonClick();

        ImageButton currentCartButton = findViewById(R.id.CurrentCartButton);
        currentCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CurrentOrderCartController.class));
                finish();
            }
        });
        ImageButton allOrdersButton = findViewById(R.id.AllOrdersButton);
        allOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AllOrdersController.class));
                finish();
            }
        });
    }

    private void orderingImageButtonClick() {
        ImageButton coffeeButton = findViewById(R.id.CoffeeOrderButton);
        coffeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CoffeeOrderController.class));
                finish();
            }
        });
        ImageButton sandwichButton = findViewById(R.id.SandwichOrderButton);
        sandwichButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SandwichOrderController.class));
                finish();
            }
        });
        ImageButton donutButton = findViewById(R.id.DonutOrderButton);
        donutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DonutOrderController.class));
                finish();
            }
        });
    }
}