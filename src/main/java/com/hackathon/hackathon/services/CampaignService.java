package com.hackathon.hackathon.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hackathon.hackathon.models.dtos.CampaignDTO;

import jakarta.annotation.PostConstruct;

@Service
public class CampaignService {
    private final List<CampaignDTO> campaigns = new ArrayList<>();

    @PostConstruct
    public void init() {
        CampaignDTO c1 = new CampaignDTO();
        c1.setId("c-nike-palermo"); c1.setBrand("Nike"); c1.setTitle("20% OFF Nike");
        c1.setDescription("20% en tienda Palermo"); c1.setCenterLat(-34.5715); c1.setCenterLon(-58.3963);
        c1.setRadiusMeters(2000); c1.setMinAge(18); c1.setCountry("AR"); c1.setPriority(10);
        c1.setCode("NIK20"); c1.setDiscount(20);

        CampaignDTO c2 = new CampaignDTO();
        c2.setId("c-starb-corrientes"); c2.setBrand("Starbucks"); c2.setTitle("Café gratis");
        c2.setDescription("Gratis con compra > $200"); c2.setCenterLat(-34.5997); c2.setCenterLon(-58.3816);
        c2.setRadiusMeters(1500); c2.setMinAge(0); c2.setCountry("AR"); c2.setPriority(8);
        c2.setCode("SBXFREE"); c2.setDiscount(100);

        CampaignDTO c3 = new CampaignDTO();
        c3.setId("c-localx"); c3.setBrand("LocalX"); c3.setTitle("10% OFF LocalX");
        c3.setDescription("10% en todo"); c3.setCenterLat(-34.5715); c3.setCenterLon(-58.3963);
        c3.setRadiusMeters(500); c3.setMinAge(0); c3.setCountry(null); c3.setPriority(6);
        c3.setCode("LX10"); c3.setDiscount(10);

        campaigns.addAll(Arrays.asList(c1, c2, c3));
    }

    public List<CampaignDTO> findApplicable(double lat, double lon, int userAge, String country) {
        return campaigns.stream()
            .filter(c -> withinGeo(c, lat, lon))
            .filter(c -> userAge == 0 || c.getMinAge() <= userAge)
            .filter(c -> c.getCountry() == null || country == null || c.getCountry().equalsIgnoreCase(country))
            .sorted(Comparator.comparingInt(CampaignDTO::getPriority).reversed())
            .collect(Collectors.toList());
    }

    private boolean withinGeo(CampaignDTO c, double lat, double lon) {
        double dist = distanceMeters(lat, lon, c.getCenterLat(), c.getCenterLon());
        return dist <= c.getRadiusMeters();
    }

    private double distanceMeters(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
}