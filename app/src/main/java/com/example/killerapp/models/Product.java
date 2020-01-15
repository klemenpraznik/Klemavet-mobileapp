package com.example.killerapp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer productId;
    private String name;
    private String shortName;
    private double purchasePrice;
    private double sellingPrice;
    private int warrantyInMonths;
    private Integer manufacturer;
    private Integer manufacturerId;
    private Integer category;
    private Integer categoryId;
    private String description;
}
