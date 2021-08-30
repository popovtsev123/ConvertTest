package com.example.testapp.views.calc_screen;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapp.IActivityListener;
import com.example.testapp.R;
import com.example.testapp.entities.Currency;
import com.example.testapp.views.currency_selector_screen.CurrencySelectorDialog;


public class CalcFragment extends Fragment implements CurrencySelectorDialog.DialogCallback {

    private EditText firstCurrencyET;
    private EditText secondCurrencyET;
    private Button firstCurrencyBtn;
    private Button secondCurrencyBtn;
    private TextView firstCurrencyTV;
    private TextView secondCurrencyTV;
    private final CurrencySelectorDialog currencySelectorDialog = new CurrencySelectorDialog(this);
    private IActivityListener activityListener;
    private int changedCurrency = 1;
    private CalcViewModel viewModel;
    private TextWatcher firstTW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            reCalcValues(1, editable.toString());
        }
    };
    private TextWatcher secondTW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            reCalcValues(2, editable.toString());
        }
    };

    public CalcFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activityListener = (IActivityListener) getActivity();
        viewModel = new ViewModelProvider(this).get(CalcViewModel.class);
        viewModel.setCurrencyInfo(activityListener.getCurrencies());
        initViews();
        setButtonsListeners();
        setTextWatchers();
    }

    @Override
    public void onSelectCurrency(int selectedPosition) {
        if (changedCurrency == 1) {
            viewModel.setFirstCurrency(selectedPosition);
            firstCurrencyTV.setText(viewModel.getCurrencyCharCode(1));
            firstCurrencyET.setText("1");
            reCalcValues(1, "1");
        }
        if (changedCurrency == 2) {
            viewModel.setSecondCurrency(selectedPosition);
            secondCurrencyTV.setText(viewModel.getCurrencyCharCode(2));
            secondCurrencyET.setText("1");
            reCalcValues(2, "1");
        }
    }

    private void initViews() {
        firstCurrencyET = requireView().findViewById(R.id.valueFirst);
        secondCurrencyET = requireView().findViewById(R.id.valueSecond);
        firstCurrencyBtn = requireView().findViewById(R.id.changeCurrencyFirst);
        secondCurrencyBtn = requireView().findViewById(R.id.changeCurrencySecond);
        firstCurrencyTV = requireView().findViewById(R.id.currencyNameFirst);
        secondCurrencyTV = requireView().findViewById(R.id.currencyNameSecond);

        Currency c1 = viewModel.getInitData().get(0);
        Currency c2 = viewModel.getInitData().get(1);

        firstCurrencyET.setText(String.valueOf(viewModel.round(0.0)));
        secondCurrencyET.setText(String.valueOf(viewModel.round(0.0)));

        firstCurrencyTV.setText(c1.getCharCode());
        secondCurrencyTV.setText(c2.getCharCode());

    }

    private void setButtonsListeners() {
        firstCurrencyBtn.setOnClickListener(view -> {
            changedCurrency = 1;
            currencySelectorDialog.show(getChildFragmentManager(), "currencySelectorDialog");
        });
        secondCurrencyBtn.setOnClickListener(view -> {
            changedCurrency = 2;
            currencySelectorDialog.show(getChildFragmentManager(), "currencySelectorDialog");
        });
    }

    private void setTextWatchers() {
        firstCurrencyET.addTextChangedListener(firstTW);
        secondCurrencyET.addTextChangedListener(secondTW);
    }

    private void removeTextWatchers() {
        firstCurrencyET.removeTextChangedListener(firstTW);
        secondCurrencyET.removeTextChangedListener(secondTW);
    }

    private void reCalcValues(int editedValue, String inputValue) {
        String value = String.valueOf(viewModel.reCalcValues(editedValue, inputValue));
        removeTextWatchers();
        if (editedValue == 1)
            secondCurrencyET.setText(value);
        if (editedValue == 2)
            firstCurrencyET.setText(value);
        setTextWatchers();
    }
}