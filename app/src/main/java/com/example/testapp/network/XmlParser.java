package com.example.testapp.network;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import com.example.testapp.entities.Currency;

public class XmlParser {

    private ArrayList<Currency> currencies;

    public XmlParser() {
        currencies = new ArrayList<>();
    }

    public ArrayList<Currency> getCurrencies() {
        return currencies;
    }

    public void parse(String xmlData) {
        Currency currentCurrency = null;
        boolean inEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagName = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("Valute".equalsIgnoreCase(tagName)) {
                            inEntry = true;
                            currentCurrency = new Currency();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (inEntry) {
                            if ("Valute".equalsIgnoreCase(tagName)) {
                                currencies.add(currentCurrency);
                                inEntry = false;
                            } else if ("NumCode".equalsIgnoreCase(tagName)) {
                                currentCurrency.setNumCode(parseInt(textValue));
                            } else if ("CharCode".equalsIgnoreCase(tagName)) {
                                currentCurrency.setCharCode(textValue);
                            } else if ("Nominal".equalsIgnoreCase(tagName)) {
                                currentCurrency.setNominal(parseInt(textValue));
                            } else if ("Name".equalsIgnoreCase(tagName)) {
                                currentCurrency.setName(textValue);
                            } else if ("Value".equalsIgnoreCase(tagName)) {
                                currentCurrency.setValue(parseDouble(textValue.replace(',', '.')));
                            }
                        }
                        break;
                    default:
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
