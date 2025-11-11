package com.hackathon.hackathon.models.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor   
@AllArgsConstructor
public class OfferResponseDTO {
    private boolean verified;
    private String message;
    private int offersCount;
    private List<CampaignDTO> offers;

}
