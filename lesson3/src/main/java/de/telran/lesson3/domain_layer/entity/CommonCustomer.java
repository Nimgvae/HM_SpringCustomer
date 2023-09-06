package de.telran.lesson3.domain_layer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonCustomer implements Customer {

    private int id;
    private String name;
    private Cart cart;
}