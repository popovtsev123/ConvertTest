package com.example.testapp.views.currency_selector_screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.entities.Currency;
import com.example.testapp.R;

import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    public static int selectedPosition = -1;
    ClickCallback callback;

    interface ClickCallback {
        void onClick(int position);
    }

    public CurrencyAdapter(ClickCallback callback) {
        this.callback = callback;
    }

    private ArrayList<Currency> items;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_currency, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Currency> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView selectImg;
        final TextView curName, curCode;
        CurrencyAdapter adapter;
        ViewHolder(View view, CurrencyAdapter adapter){
            super(view);
            this.adapter = adapter;
            selectImg = (ImageView)view.findViewById(R.id.selectImg);
            curName = (TextView) view.findViewById(R.id.curName);
            curCode = (TextView) view.findViewById(R.id.curCode);
        }

        void bind(Currency item, int position) {
            curName.setText(item.getName());
            curCode.setText(item.getCharCode());
            itemView.setOnClickListener(view -> {
                CurrencyAdapter.selectedPosition = position;
                adapter.callback.onClick(position);
                adapter.notifyDataSetChanged();

            });
            if (selectedPosition == position) {
                selectImg.setVisibility(View.VISIBLE);
            } else {
                selectImg.setVisibility(View.GONE);
            }
        }
    }
}
