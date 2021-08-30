package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.example.testapp.entities.Currency;
import com.example.testapp.network.NetworkManager;
import com.example.testapp.network.XmlParser;
import com.example.testapp.views.currency_selector_screen.CurrencySelectorDialog;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IActivityListener {

    private final NetworkManager networkManager = new NetworkManager();
    private final XmlParser xmlParser = new XmlParser();
    ArrayList<Currency> currencyInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Navigation.findNavController(this, R.id.mainContainer).navigate(R.id.loaderFragment);
        try {
            downloadCurrencies();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Currency> getCurrencies() {
        return currencyInfo;
    }

    private void downloadCurrencies() throws IOException {
        new Thread(() -> {
            String result = null;
            try {
                result = networkManager.download("https://www.cbr.ru/scripts/XML_daily.asp");
            } catch (IOException e) {
                e.printStackTrace();
            }
            xmlParser.parse(result);
            currencyInfo = xmlParser.getCurrencies();
            runOnUiThread(this::goToCalculator);
        }).start();
    }

    private void goToCalculator() {
        Navigation.findNavController(this, R.id.mainContainer).navigate(R.id.calcFragment);
    }
}
