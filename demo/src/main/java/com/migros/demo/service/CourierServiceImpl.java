package com.migros.demo.service;

import com.migros.demo.entity.Courier;
import com.migros.demo.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CourierServiceImpl implements CourierService {

    // Just JPA methods

    @Autowired
    CourierRepository courierRepository;

    @Override
    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();
    }

    @Override
    public Courier save(Courier courier) {
        return courierRepository.save(courier);
    }

    @Override
    public Optional<Courier> findById(Long courierId) {
        return courierRepository.findById(courierId);
    }

    @Override
    public void deleteById(Long courierId) {
        courierRepository.deleteById(courierId);
    }

}

