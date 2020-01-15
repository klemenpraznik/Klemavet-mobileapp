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
public class Article {
    private Integer articleId;
    private Document document;
    private Integer documentId;
    private Product product;
    private Integer productId;
    private double quantity;
    private double price;
    private double discount;
    private double taxRate;
    private double assemblyPrice;
}
