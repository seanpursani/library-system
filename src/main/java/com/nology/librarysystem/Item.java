package com.nology.librarysystem;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Item {

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    @JsonAlias(value = "details")
    private Object details;
    private boolean currentlyLoaned = false;
    private int amountLoaned = 0;

    public Item(Object object) {
        this.details = object;
    }

    public Item() {
    }

    public boolean isCurrentlyLoaned() {
        return currentlyLoaned;
    }

    public int getAmountLoaned() {
        return amountLoaned;
    }
}
