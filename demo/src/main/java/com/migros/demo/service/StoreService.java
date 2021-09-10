package com.migros.demo.service;

import com.migros.demo.entity.Store;
import java.util.List;
import java.util.Optional;

// (Dependency Inversion) => Abstraction Layer for Controller and Service
public interface StoreService {

    List<Store> getAllStores();

    Optional<Store> findById(Long storeId);

    Store save(Store store);

    void deleteById(Long storeId);

}
