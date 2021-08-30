package com.example.testapp.views.calc_screen;

import androidx.lifecycle.ViewModel;

import com.example.testapp.entities.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class CalcViewModel extends ViewModel {

    private ArrayList<Currency> currencyInfo;
    private Currency firstCurrency;
    private Currency secondCurrency;

    public Currency getFirstCurrency() {
        return firstCurrency;
    }

    public void setFirstCurrency(int position) {
        firstCurrency = currencyInfo.get(position);
    }

    public Currency getSecondCurrency() {
        return secondCurrency;
    }

    public void setSecondCurrency(int position) {
        secondCurrency = currencyInfo.get(position);
    }

    public String getCurrencyCharCode(int currencyNumber) {
        if (currencyNumber == 1)
            return firstCurrency.getCharCode();
        else
            return secondCurrency.getCharCode();
    }

    public double reCalcValues(int editedValue, String inputValue) {
        double value = 0.0;

        if (firstCurrency != null && secondCurrency != null) {
            double crossCourse;
            switch (editedValue) {
                case 1:
                    crossCourse = firstCurrency.getValue() / secondCurrency.getValue();
                case 2:
                    crossCourse = secondCurrency.getValue() / firstCurrency.getValue();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + editedValue);
            }
            if (inputValue.length() != 0)
                value = Double.parseDouble(inputValue) * crossCourse;
        }
        return round(value);
    }

    public ArrayList<Currency> getCurrencyInfo() {
        return currencyInfo;
    }

    public void setCurrencyInfo(ArrayList<Currency> currencyInfo) {
        this.currencyInfo = currencyInfo;
    }

    public ArrayList<Currency> getInitData() {
        if (currencyInfo.size() < 2) return null;
        ArrayList<Currency> initCurrencies = new ArrayList(2);

        firstCurrency = currencyInfo.get(0);
        secondCurrency = currencyInfo.get(1);
        initCurrencies.add(firstCurrency);
        initCurrencies.add(secondCurrency);
        return initCurrencies;
    }


    double round(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(4, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
