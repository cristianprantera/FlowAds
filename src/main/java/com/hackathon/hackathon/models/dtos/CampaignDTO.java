package com.hackathon.hackathon.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor   
@AllArgsConstructor

public class CampaignDTO {
    private String id;
    private String brand;
    private String title;
    private String description;
    private double centerLat;
    private double centerLon;
    private double radiusMeters;
    private int minAge;
    private String country;
    private int priority;
    private String code;
    private int discount;
}