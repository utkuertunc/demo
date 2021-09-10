package com.migros.demo.controller;

import com.migros.demo.entity.Store;
import com.migros.demo.exception.StoreNotFoundException;
import com.migros.demo.service.StoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/stores")
public class StoreController {

    @Autowired
    StoreService storeService;

    @ApiOperation(value = "GET ALL STORES")
    @GetMapping(value = "/list")
    public List<Store> getAllStores(){
        return storeService.getAllStores();
    }

    @ApiOperation(value = "GET A STORE")
    @GetMapping(value = "/get")
    public Store getStore(@RequestParam(value = "id") Long id){
        return storeService.findById(id)
                           .orElseThrow(()-> new StoreNotFoundException("Store with id: " + id + " is Not Found!"));
    }

    @ApiOperation(value = "ADD STORE")
    @PostMapping(value = "/add")
    public Store addStore(@Valid @RequestBody Store store){
        return storeService.save(store);
    }

    @ApiOperation(value = "DELETE STORE")
    @DeleteMapping(value = "/delete")
    public String deleteStore(@RequestParam(value = "id") Long id){
        Store store = storeService.findById(id)
                                  .orElseThrow(()-> new StoreNotFoundException("Store with id: " + id + " is Not Found!"));
        storeService.deleteById(id);
        return "Store with id: " + id + " is deleted.";
    }
}
