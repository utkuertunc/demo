package com.migros.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.migros.demo.service.MathService;
import com.migros.demo.service.MathServiceImpl;
import com.migros.demo.service.StoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MathServiceTest {
    //Testing with JUnit and Mockito

    @Autowired
    MathService mathService;

    @Test
    public void calculateHaversine_success() throws Exception {
        //Test for Hoversine Formulae Calculation

        double lat1 = 40.99233070;
        double lat2 = 29.12442290;
        double lgt1 = 35.99233070;
        double lgt2 = 20.12442290;

        assertEquals(1950523.86192355, mathService.haversine(lat1, lgt1, lat2, lgt2)
                , 0.01);

    }

    @Test
    public void randomCustomerLat_success() throws Exception {
        //Test for Random Lat is double

        String str = mathService.randomCustomerLat().getClass().getName();
        assertEquals(str, "java.lang.Double");

    }

    @Test
    public void randomCustomerLgt_success() throws Exception {
        //Test for Random Lgt is double

        String str = mathService.randomCustomerLgt().getClass().getName();
        assertEquals(str, "java.lang.Double");
    }
}
