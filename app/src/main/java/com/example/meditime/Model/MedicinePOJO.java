package com.example.meditime.Model;

public class MedicinePOJO {
    String medcineName;
    String amount;
    String type;

    public MedicinePOJO(String medcineName, String amount, String type) {
        this.medcineName = medcineName;
        this.amount = amount;
        this.type = type;
    }

    public String getMedcineName() {
        return medcineName;
    }

    public void setMedcineName(String medcineName) {
        this.medcineName = medcineName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
