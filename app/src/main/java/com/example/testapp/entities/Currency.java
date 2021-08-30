package com.example.testapp.entities;

public class Currency {
    private int NumCode;
    private String CharCode;
    private int Nominal;
    private String Name;
    private double Value;

    public Currency() {
        this.NumCode = 0;
        this.CharCode = "";
        this.Nominal = 0;
        this.Name = "";
        this.Value = 0.0;
    }

    public Currency(int numCode, String charCode, int nominal, String name, double value) {
        this.NumCode = numCode;
        this.CharCode = charCode;
        this.Nominal = nominal;
        this.Name = name;
        this.Value = value;
    }

    public int getNumCode() {
        return NumCode;
    }

    public void setNumCode(int numCode) {
        NumCode = numCode;
    }

    public String getCharCode() {
        return CharCode;
    }

    public void setCharCode(String charCode) {
        CharCode = charCode;
    }

    public int getNominal() {
        return Nominal;
    }

    public void setNominal(int nominal) {
        Nominal = nominal;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }
}
