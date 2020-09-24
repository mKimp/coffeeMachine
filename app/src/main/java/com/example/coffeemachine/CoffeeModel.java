package com.example.coffeemachine;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class CoffeeModel   {


    private int id;
    private String date;
    private String coffeeBrand;
    private String coffeeMilk;
    private String coffeeBrew;
    private String notes;
    private String image;

    public CoffeeModel(int id, String date, String coffeeBrand, String coffeeMilk, String coffeeBrew, String notes, String image) {
        this.id = id;
        this.date = date;
        this.coffeeBrand = coffeeBrand;
        this.coffeeMilk = coffeeMilk;
        this.coffeeBrew = coffeeBrew;
        this.notes = notes;
        this.image = image;
    }

    @Override
    public String toString() {
        return "CoffeeModel{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", coffeeBrand='" + coffeeBrand + '\'' +
                ", coffeeMilk='" + coffeeMilk + '\'' +
                ", coffeeBrew='" + coffeeBrew + '\'' +
                ", notes='" + notes + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
