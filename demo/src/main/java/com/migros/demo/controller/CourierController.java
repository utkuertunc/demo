package com.migros.demo.controller;

import com.migros.demo.entity.Courier;
import com.migros.demo.entity.Store;
import com.migros.demo.exception.CourierNotFoundException;
import com.migros.demo.repository.CourierRepository;
import com.migros.demo.service.MathService;
import com.migros.demo.service.CourierService;

import com.migros.demo.service.StoreService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/couriers")
public class CourierController {

    @Autowired
    CourierService courierService;

    @Autowired
    MathService mathService;

    @Autowired
    StoreService storeService;

    @ApiOperation(value = "GET ALL COURIERS")
    @GetMapping(value = "/list")
    public List<Courier> getAllCouriers() {
        return courierService.getAllCouriers();
    }

    @ApiOperation(value = "ADD COURIER")
    @PostMapping(value = "/add")
    public Courier addCourier(@Valid @RequestBody Courier courier) {
        return courierService.save(courier);
    }

    @ApiOperation(value = "DELETE COURIER")
    @DeleteMapping(value = "/delete")
    public String deleteCourier(@RequestParam(value = "id") Long id) {
        Courier courier = courierService.findById(id)
                .orElseThrow(() -> new CourierNotFoundException("Store with id: " + id + " is Not Found!"));
        courierService.deleteById(id);
        return "Store with id: " + id + " is deleted.";
    }

    @ApiOperation(value = "GET A COURIER")
    @GetMapping(value = "/get")
    public Courier getCourierById(@RequestParam(value = "id") Long id) {
        Courier courier = courierService.findById(id).orElseThrow(() -> new CourierNotFoundException("Courier with id: " + id + " is Not Found!"));
        return courier;
    }

    @ApiOperation(value = "GET COURIER'S TOTAL DISTANCE")
    @GetMapping(value = "/getTotalDistance")
    public String getTotalDistance(@RequestParam(value = "selectCourier") Long selectCourier) {
        List<Courier> courier = courierService.getAllCouriers();

        try {
            courier.get(selectCourier.intValue()).getTotalDistance();
            return courier.get(selectCourier.intValue()).getTotalDistance() + "Meters";

        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException("Courier not found! Value must be between of 0 to " + courier.size() + "\n\n");
        }

    }


    // URI has 4 parameters.
    // time is path variable
    // selectCourier is choose for index of Object List. Starts at 0
    // courierLat and courierLgt are courier latitude and longtitude
    @ApiOperation(value = "NAVIGATE COURIER")
    @PutMapping(value = "/navigate/{time}")
    public String navigateCourier(@Valid @RequestParam(value = "selectCourier") int selectCourier, @RequestParam(value = "courierLat") Double courierLat, @RequestParam(value = "courierLgt") Double courierLgt, @PathVariable(value = "time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time) {

        //Objects Lists for get and set
        List<Courier> courier = courierService.getAllCouriers();
        List<Store> store = storeService.getAllStores();

        try {
            courier.get(selectCourier);
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException("Courier not found! Value must be between of 0 to " + courier.size() + "\n\n");
        }

        // Time difference between parameter and last value
        long timeDifference = ChronoUnit.SECONDS.between(courier.get(selectCourier).getTime(), time);

        log.info("----------------------");
        log.info("Time difference is ==> {} Seconds", timeDifference);

        //  Checking 60 second cooldown for new navigation
        if (timeDifference > 60) {
            //log.info("Time difference is checked ==> {} ", timeDifference);
            // Update last time to parameter time
            courier.get(selectCourier).setTime(time);

            // Checking nearest store
            for (Store value : store) {
                //BigDecimal to double convertion
                double storeLat = value.getStoreLat().doubleValue();
                double storeLgt = value.getStoreLgt().doubleValue();

                // Calculating distance between courier to every store
                double courierToStore = mathService.haversine(courierLat, courierLgt, storeLat, storeLgt);
                log.info("Courier to store distance ==> {} meters", courierToStore);

                // Checking 100 meter rule
                if (courierToStore < 100) {
                    log.info("Store distance is checked");

                    // Define new variables for clean code
                    double customerLat = mathService.randomCustomerLat();
                    double customerLgt = mathService.randomCustomerLgt();

                    // Calculating distance between store to customer
                    double storeToCustomer = mathService.haversine(storeLat, storeLgt, customerLat, customerLgt);
                    log.info("Customer coordinate ==> ({}, {})", customerLat, customerLgt);
                    log.info("Store to customer distance ==> {}", storeToCustomer);

                    // Update courier location to store location for last location of courier
                    courier.get(selectCourier).setCourierLat(BigDecimal.valueOf(customerLat));
                    courier.get(selectCourier).setCourierLgt(BigDecimal.valueOf(customerLgt));
                    log.info("Courier new coordinate ==> ({}, {})", courier.get(selectCourier).getCourierLat(), courier.get(selectCourier).getCourierLgt());

                    // Calculate total distance and update
                    double totalDistanceNow = courierToStore + storeToCustomer;
                    courier.get(selectCourier).setTotalDistance(totalDistanceNow);
                    log.info("Total Distance ==> {} Meters", totalDistanceNow);

                    //Save changes
                    courierService.save(courier.get(selectCourier));
                    return "Courier Operations Are Succesfully Done";
                } else {
                    return "There Is No Store 100 Meters Of Near The Courier ";
                }
            }
        } else if (timeDifference < 0) {
            return "Input Date Is Past";
        } else {
            return "Wait 60 Seconds Cooldown or Choose Another Time ";
        }
        return "Put Request Is Done";

    }
}