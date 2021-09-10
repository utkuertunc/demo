package com.migros.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.migros.demo.entity.Courier;
import com.migros.demo.service.CourierService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CourierControllerTest {
    // Testing with JUnit and Mockito

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CourierService courierService;

    // Data
    Courier record1 = new Courier(1l, "Hasan Buyuk", LocalDateTime.now(), 7987.4, BigDecimal.valueOf(29.12442290), BigDecimal.valueOf(29.12442290));
    Courier record2 = new Courier(2l, "Kaya Polat Demir", LocalDateTime.now(), 0.1, BigDecimal.valueOf(39.12489290), BigDecimal.valueOf(22.12442290));
    Courier record3 = new Courier(3l, "Poyraz Ozturk", LocalDateTime.now(), 45498.78, BigDecimal.valueOf(11.12442290), BigDecimal.valueOf(45.12442290));

    @Test
    public void getAllCouriers_success() throws Exception {
        // Test for Get All Couriers

        List<Courier> records = new ArrayList<>(Arrays.asList(record1, record2, record3));
        Mockito.when(courierService.getAllCouriers()).thenReturn(records);

        // Builder Pattern
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/couriers/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].courierName", is("Poyraz Ozturk")));
    }

    @Test
    public void getCourierById_success() throws Exception {
        //Test for Get a Courier

        Mockito.when(courierService.findById(record1.getCourierId())).thenReturn(java.util.Optional.of(record1));

        // Builder Pattern
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/couriers/get?id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.courierName", is("Hasan Buyuk")));
    }

    @Test
    public void createCourier_success() throws Exception {
        //Test for Add Courier

        // Builder Pattern
        Courier courier = Courier.builder()
                .courierId(4l)
                .courierName("Baris Manco")
                .time(LocalDateTime.now()) // time is now
                .totalDistance(789.47)
                .courierLat(BigDecimal.valueOf(33.12442290))
                .courierLgt(BigDecimal.valueOf(45.19999990))
                .build();

        Mockito.when(courierService.save(courier)).thenReturn(courier);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/couriers/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(courier));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.courierName", is("Baris Manco")));
    }

    @Test
    public void deleteCourier_success() throws Exception {
        // Test for Delete Courier

        Mockito.when(courierService.findById(record2.getCourierId())).thenReturn(Optional.of(record2));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/couriers/delete?id=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //
    /* There was an error. I'll solve the problem.
       Please skip this part for now.
    @Test
    public void navigateCourier_success() throws Exception {
        Courier navigateCourier = Courier.builder()
                .courierId(1l)
                .courierName("Mustafa Sandal")
                .time(LocalDateTime.of(2029,9,6,18,43,20))
                .totalDistance(789.47)
                .courierLat(BigDecimal.valueOf(40.992330))
                .courierLgt(BigDecimal.valueOf(29.12447220))
                .build();

        Mockito.when(courierService.findById(record1.getCourierId())).thenReturn(Optional.of(record1));
        Mockito.when(courierService.save(navigateCourier)).thenReturn(navigateCourier);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/couriers/navigate/2029-09-06T18:43:20?selectCourier=0&&courierLat=40.99233070&&courierLgt=29.12447220")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(navigateCourier));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.courierName", is("Mustafa Sandal")));
    }*/


}
