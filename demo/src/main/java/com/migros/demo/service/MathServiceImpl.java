package com.migros.demo.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MathServiceImpl implements MathService {

    // Service for big aritmetichal operations

    @Override
    public Double haversine(Double lat1, Double lgt1, Double lat2, Double lgt2) {

        // Haversine Formulae is calculating distance between two coordinate on Earth
        // Distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lgt2 - lgt1);

        // Convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371; //Radius of the Earth
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c * 1000; // * 1000 for meters
    }


    @Override
    public Double randomCustomerLat() {
        // Create random latitude for Customer
        Random random = new Random();

        // closer values for range
        double start = 40.96324630, end = 41;
        return start + (random.nextDouble() * (end - start));
    }

    @Override
    public Double randomCustomerLgt() {
        // Create random longtitude for Customer
        Random random = new Random();

        // closer values for range
        double start = 29.02102920, end = 29.11612930;
        return start + (random.nextDouble() * (end - start));
    }

}
