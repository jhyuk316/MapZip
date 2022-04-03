package com.jhyuk316.mapzip.controller;

import com.jhyuk316.mapzip.service.MapzipService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
// 스프링의 모든 빈을 로드하여 테스트 실행
// @SpringBootTest
// 해당 컨트롤러 클래스만 로드하여 테스트 실행
@WebMvcTest(controllers = MapzipController.class)
public class MapzipControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    MapzipService service;


    @Test
    void testHello() throws Exception {
        String hello = "hello";

        mvc.perform(get("/mapzip/hello")).andExpect(status().isOk())
            .andExpect(content().string(hello));

    }


    @Test
    void getRestaurantList() throws Exception {

    }

    @Test
    @DisplayName("DTO 리턴")
    void addRestarurant() throws Exception{
        String name = "테스트 식당";
        String address = "서울특별시 관악구 청룡동 남부순환로 1820";

        mvc.perform(get("/mapzip/addRestaurant").param("name", name).param("address", address))
            .andExpect(status().isOk()).andExpect(jsonPath("$.restaurantname", is(name)))
            .andExpect(jsonPath("$.address", is(address)));

    }
}
