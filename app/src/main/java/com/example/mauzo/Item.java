package com.example.mauzo;

public class Item {
    String date,sales,expenses,margin,key;


    public Item(String date, String sales, String expenses, String margin, String key) {
        this.date = date;
        this.sales = sales;
        this.expenses = expenses;
        this.margin = margin;
        this.key = key;
    }

    public Item() {
    }

    public String getDate() {
        return date;
    }

    public String getSales() {
        return sales;
    }

    public String getExpenses() {
        return expenses;
    }

    public String getMargin() {
        return margin;
    }

    public String getKey() {
        return key;
    }
}
