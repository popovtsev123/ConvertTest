package com.example.testapp.views.currency_selector_screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.testapp.IActivityListener;
import com.example.testapp.MainActivity;
import com.example.testapp.R;

public class CurrencySelectorDialog extends DialogFragment {

    public interface DialogCallback {
        void onSelectCurrency(int selectedPosition);
    }

    private DialogCallback callback;
    private int selectedPosition = 0;

    public CurrencySelectorDialog(DialogCallback callback) {
        this.callback = callback;
    }

    RecyclerView recyclerView;
    CurrencyAdapter adapter;
    ImageButton closeBtn;
    ImageButton confirmBtn;
    IActivityListener activityListener;


    /**
     *
     * Чтоб диалог был во весь экран
     * https://stackoverflow.com/questions/7189948/full-screen-dialogfragment-in-android
     * */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL,
                android.R.style.Theme_Light_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_currency_selector, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activityListener = (MainActivity) getActivity();
        initAdapter();
        initButtons();
    }

    private void initButtons() {
        closeBtn = requireView().findViewById(R.id.closeDlg);
        confirmBtn = requireView().findViewById(R.id.confirmDlg);

        closeBtn.setOnClickListener(view -> {
            dismiss();
        });

        confirmBtn.setOnClickListener(view -> {
            callback.onSelectCurrency(selectedPosition);
            dismiss();
        });
    }

    private void initAdapter() {
        adapter = new CurrencyAdapter(position -> {
            selectedPosition = position;

        });

        recyclerView = requireView().findViewById(R.id.currencyRV);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setItems(activityListener.getCurrencies());
    }
}