package com.migros.demo.service;

public interface MathService {

    Double haversine(Double lat1, Double lgt1, Double lat2, Double lgt2);

    Double randomCustomerLat();

    Double randomCustomerLgt();

}