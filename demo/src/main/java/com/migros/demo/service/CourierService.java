package com.migros.demo.service;

import com.migros.demo.entity.Courier;

import java.util.List;
import java.util.Optional;

// (Dependency Inversion) => Abstraction Layer for Controller and Service
public interface CourierService {

    List<Courier> getAllCouriers();

    Courier save(Courier courier);

    Optional<Courier> findById(Long courierId);

    void deleteById(Long courierId);

}
