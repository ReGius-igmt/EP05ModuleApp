package ru.ket.EP05.T5.model;

public class Country {
    private int id;
    private int price;
    private String name;

    public Country(int id, int price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
