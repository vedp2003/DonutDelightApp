package com.example.rucafeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DonutAdapter extends RecyclerView.Adapter<DonutAdapter.ViewHolder> {

    private final ArrayList<MenuItem> donutList;
    private final Context context;
    private final int[] imageResourceIds;

    public DonutAdapter(ArrayList<MenuItem> donutList, Context context, int[] imageResourceIds) {
        this.donutList = donutList;
        this.context = context;
        this.imageResourceIds = imageResourceIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donut, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Donut donut = (Donut) donutList.get(position);
        donut.setQuantity(1);
        holder.donutImageView.setImageResource(imageResourceIds[position]);
        holder.donutNameTextView.setText(donut.getFlavor() + " " + donut.getType());
        holder.donutPriceTextView.setText(String.format("Price: $%.2f", donut.price()));

        ArrayAdapter<Integer> quantityAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getQuantities());
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.donutQuantitySpinner.setAdapter(quantityAdapter);

        holder.donutQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int spinnerPosition, long id) {
                int quantity = (int) parent.getItemAtPosition(spinnerPosition);
                donut.setQuantity(quantity);
                double price = donut.price();
                holder.donutPriceTextView.setText(String.format("Price: $%.2f", price));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        holder.addDonutButton.setOnClickListener(v -> {
            int quantity = (int) holder.donutQuantitySpinner.getSelectedItem();
            donut.setQuantity(quantity);
            double price = donut.price();
            ((DonutOrderController) context).addDonutToOrder(donut, price);

        });
    }

    @Override
    public int getItemCount() {
        return donutList.size();
    }

    private List<Integer> getQuantities() {
        List<Integer> quantities = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            quantities.add(i);
        }
        return quantities;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView donutImageView;
        TextView donutNameTextView, donutPriceTextView;
        Spinner donutQuantitySpinner;
        Button addDonutButton;

        public ViewHolder(View itemView) {
            super(itemView);
            donutImageView = itemView.findViewById(R.id.donutImageView);
            donutNameTextView = itemView.findViewById(R.id.donutNameTextView);
            donutPriceTextView = itemView.findViewById(R.id.donutPriceTextView);
            donutQuantitySpinner = itemView.findViewById(R.id.donutQuantitySpinner);
            addDonutButton = itemView.findViewById(R.id.addDonutButton);
        }
    }
}