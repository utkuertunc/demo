package com.migros.demo.service;

import com.migros.demo.entity.Store;
import com.migros.demo.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService{

    // Just JPA Methods

    @Autowired
    StoreRepository storeRepository;

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Optional<Store> findById(Long storeId) {
        return storeRepository.findById(storeId);
    }

    @Override
    public Store save(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public void deleteById(Long storeId) {
        storeRepository.deleteById(storeId);
    }
}
