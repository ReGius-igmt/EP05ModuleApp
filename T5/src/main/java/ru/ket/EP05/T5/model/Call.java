package ru.ket.EP05.T5.model;

public class Call {
    private String phone;
    private int countryCode;
    private int minutes;

    public Call(String phone, int countryCode, int minutes) {
        this.phone = phone;
        this.countryCode = countryCode;
        this.minutes = minutes;
    }

    public String getPhone() {
        return phone;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public int getMinutes() {
        return minutes;
    }
}
