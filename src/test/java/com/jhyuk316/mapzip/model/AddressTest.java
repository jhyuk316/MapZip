package com.jhyuk316.mapzip.model;

import com.jhyuk316.mapzip.ApiKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Value("${naver-map.Client.Id}")
    private String naverClientId;

    @Value("${naver-map.Client.Secret}")
    private String naverClientSecret;

    @Test
    void setAddress() {

        System.out.println("naverClientId = " + naverClientId);
        System.out.println("naverClientSecret = " + naverClientSecret);

        // address.setAddress("경기도 성남시 분당구 불정로 6 그린팩토리");



    }
}