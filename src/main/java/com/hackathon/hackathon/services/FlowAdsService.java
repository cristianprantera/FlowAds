package com.hackathon.hackathon.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hackathon.hackathon.models.dtos.CampaignDTO;
import com.hackathon.hackathon.models.dtos.OfferRequestDTO;
import com.hackathon.hackathon.models.dtos.OfferResponseDTO;
import com.hackathon.hackathon.utils.ApiClient;

@Service
public class FlowAdsService {

    private final ApiClient apiClient;
    private final CampaignService campaignService;

    public FlowAdsService(ApiClient apiClient, CampaignService campaignService) {
        this.apiClient = apiClient;
        this.campaignService = campaignService;
    }

    // flujo: cuando el usuario presiona "Simular llegada"
    public OfferResponseDTO generarOfertaEnElMomento(OfferRequestDTO req) {
        OfferResponseDTO resp = new OfferResponseDTO();

        // 1️ Verificar ubicación
        String locResult = apiClient.verificarUbicacion(req.getPhoneNumber(), req.getLatitude(), req.getLongitude(), 10000);
        if (!"TRUE".equalsIgnoreCase(locResult)) {
            resp.setVerified(false);
            resp.setMessage("Fuera de la zona: verificationResult=" + locResult);
            return resp;
        }

        // 2️ Verificar número (si está habilitado)
        Boolean numeroOk = apiClient.verificarNumero(req.getPhoneNumber());
        if (numeroOk != null && !numeroOk) {
            resp.setVerified(false);
            resp.setMessage("Número no verificado por operador.");
            return resp;
        }

        // 3️ Verificar estado de línea
        Boolean lineaOk = apiClient.verificarEstadoLinea(req.getPhoneNumber());
        if (lineaOk != null && !lineaOk) {
            resp.setVerified(false);
            resp.setMessage("Línea no activa / no conectada.");
            return resp;
        }

        // 4️ SIM swap check
        Boolean noSwap = apiClient.verificarSimReciente(req.getPhoneNumber(), 240);
        if (noSwap != null && !noSwap) {
            resp.setVerified(false);
            resp.setMessage("Riesgo: cambio de SIM detectado recientemente.");
            return resp;
        }

        // 5️ KYC (opcional)
        int userAge = 25;
        String country = "AR";
        Boolean kycOk = apiClient.verificarKycMatch(req.getPhoneNumber(), null);
        if (kycOk != null && !kycOk) {
            resp.setVerified(false);
            resp.setMessage("KYC no verificado.");
            return resp;
        }

        // 6️ Buscar campañas aplicables
        List<CampaignDTO> aplicables = campaignService.findApplicable(req.getLatitude(), req.getLongitude(), userAge, country);
        if (aplicables.isEmpty()) {
            resp.setVerified(true);
            resp.setMessage("No hay campañas activas para tu ubicación.");
            resp.setOffersCount(0);
            return resp;
        }

        resp.setVerified(true);
        resp.setMessage("Campañas encontradas: " + aplicables.size());
        resp.setOffersCount(aplicables.size());
        resp.setOffers(aplicables);
        return resp;
    }
}
