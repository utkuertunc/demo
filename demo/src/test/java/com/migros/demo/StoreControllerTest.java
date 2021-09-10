package com.migros.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.migros.demo.entity.Store;
import com.migros.demo.service.StoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StoreControllerTest {
    // Testing with JUnit and Mockito

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    StoreService storeService;

    // Data
    Store record1 = new Store(1l, "Ataşehir MMM Migros", BigDecimal.valueOf(40.99233070), BigDecimal.valueOf(29.12442290));
    Store record2 = new Store(2l, "Novada MMM Migros", BigDecimal.valueOf(40.98610600), BigDecimal.valueOf(29.11612930));
    Store record3 = new Store(3l, "Beylikdüzü 5M Migros", BigDecimal.valueOf(41.00668510), BigDecimal.valueOf(28.65522620));

    @Test
    public void getAllRecords_success() throws Exception {
        // Test for get All Stores
        List<Store> records = new ArrayList<>(Arrays.asList(record1, record2, record3));
        Mockito.when(storeService.getAllStores()).thenReturn(records);

        // Builder Pattern
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/stores/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].storeName", is("Beylikdüzü 5M Migros")));
    }

    @Test
    public void getStoreById_success() throws Exception {
        //Test for Get a Store

        Mockito.when(storeService.findById(record1.getStoreId())).thenReturn(java.util.Optional.of(record1));

        // Builder Pattern
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/stores/get?id=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.storeName", is("Ataşehir MMM Migros")));
    }

    @Test
    public void createStore_success() throws Exception {
        //Test for Add Store

        // Builder Pattern
        Store store = Store.builder()
                .storeId(4l)
                .storeName("Migros")
                .storeLat(BigDecimal.valueOf(33.12442290))
                .storeLgt(BigDecimal.valueOf(45.19999990))
                .build();

        Mockito.when(storeService.save(store)).thenReturn(store);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/stores/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(store));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.storeName", is("Migros")));
    }

    @Test
    public void deleteStore_success() throws Exception {
        // Test for Delete Store

        Mockito.when(storeService.findById(record2.getStoreId())).thenReturn(Optional.of(record2));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/stores/delete?id=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
