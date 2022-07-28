package com.jhyuk316.mapzip;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@ToString
public class ApiKey {

    @Value("${naver-map.Client.Id}")
    private String naverClientId;

    @Value("${naver-map.Client.Secret}")
    private String naverClientSecret;

}
