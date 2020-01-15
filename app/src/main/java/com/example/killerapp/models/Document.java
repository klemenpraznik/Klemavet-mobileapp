package com.example.killerapp.models;

import java.util.Date;

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
public class Document {
    private Integer documentId;
    private Client client;
    private Integer clientId;

    //predračun podatki
    private Date offerDate;
    private Integer offerValidityDays;
    private Date offerDateOfOrder;

    //račun podatki
    private Date invoiceDate;
    private Date invoiceServiceFrom;
    private Date invoiceServiceUntil;
    private Date invoiceDateOfMaturity;
    private Date invoiceDateOfOrder;

    //dobavnica
    private Date deliveryNoteDate;

    //cene
    private Double totalExcludingVAT;
    private Double discountPercent;
    private Double discountAmount;
    private Double amountExcludingVAT;
    private Double amountIncludingVAT;

    //status
    private Double paidAmount;
    private Double assemblyPrice;
}
