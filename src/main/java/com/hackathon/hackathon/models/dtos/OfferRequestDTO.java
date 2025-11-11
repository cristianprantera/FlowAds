package com.hackathon.hackathon.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor   
@AllArgsConstructor
public class OfferRequestDTO {
    private String phoneNumber;
    private double latitude;
    private double longitude;
}
