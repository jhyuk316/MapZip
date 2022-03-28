package com.jhyuk316.mapzip.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
// 스프링의 모든 빈을 로드하여 테스트 실행
// @SpringBootTest
// 해당 컨트롤러 클래스만 로드하여 테스트 실행
@WebMvcTest(controllers = MapzipController.class)
public class MapzipControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testHello() throws Exception {
        String hello = "hello";

        mvc.perform(get("/mapzip/hello")).andExpect(status().isOk())
                .andExpect(content().string(hello));

    }
}
