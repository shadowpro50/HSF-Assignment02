package com.se192491.assignment02.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OrderForm {
    private List<Integer> orchidIds = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();

    // Helper method to add item
    public void addItem(int orchidId, int quantity) {
        this.orchidIds.add(orchidId);
        this.quantities.add(quantity);
    }
}