package com.example.coffeemachine;

public class CoffeeModel {


    String date;
    String coffeeBrand;
    String coffeeMilk;
    String coffeeBrew;
    String notes;

    public CoffeeModel(String date, String coffeeBrand, String coffeeMilk, String coffeeBrew, String notes) {
        this.date = date;
        this.coffeeBrand = coffeeBrand;
        this.coffeeMilk = coffeeMilk;
        this.coffeeBrew = coffeeBrew;
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCoffeeBrand() {
        return coffeeBrand;
    }

    public void setCoffeeBrand(String coffeeBrand) {
        this.coffeeBrand = coffeeBrand;
    }

    public String getCoffeeMilk() {
        return coffeeMilk;
    }

    public void setCoffeeMilk(String coffeeMilk) {
        this.coffeeMilk = coffeeMilk;
    }

    public String getCoffeeBrew() {
        return coffeeBrew;
    }

    public void setCoffeeBrew(String coffeeBrew) {
        this.coffeeBrew = coffeeBrew;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
