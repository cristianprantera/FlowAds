package com.hackathon.hackathon.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.hackathon.models.dtos.OfferRequestDTO;
import com.hackathon.hackathon.models.dtos.OfferResponseDTO;
import com.hackathon.hackathon.services.FlowAdsService;

@RestController
@RequestMapping("/flowads")
public class FlowAdsController {

    private final FlowAdsService service;

    public FlowAdsController(FlowAdsService service) {
        this.service = service;
    }

    // Endpoint que el frontend (botón) llama: "Simular llegada ahora"
    @PostMapping("/oferta")
    public ResponseEntity<OfferResponseDTO> ofertaNow(@RequestBody OfferRequestDTO request) {
        OfferResponseDTO response = service.generarOfertaEnElMomento(request);
        return ResponseEntity.ok(response);
    }

    // Opcional: listar campaigns (admin)
    @GetMapping("/admin/campaigns")
    public ResponseEntity<?> campaigns() {
        return ResponseEntity.ok(service.getClass().getName()); // placeholder, lo tenés en CampaignService
    }
}
