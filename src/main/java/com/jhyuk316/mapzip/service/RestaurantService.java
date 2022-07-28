package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.ApiKey;
import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ApiKey apiKey;

    public Long save(RestaurantDTO restaurantDTO) {
        if (!isValidRestaurant(restaurantDTO)) {
            throw new IllegalArgumentException("올바르지 않는 식당 정보입니다.");
        }

        log.debug("Tring to save restaurantDTO.getName() = " + restaurantDTO.getName());
        log.debug("Tring to save restaurantDTO.getAddress() = " + restaurantDTO.getAddress());

        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(restaurantDTO.getName())
                .address(restaurantDTO.getAddress())
                .latitude(restaurantDTO.getLatitude())
                .longitude(restaurantDTO.getLongtitude())
                .build();

        restaurantRepository.save(restaurant);

        return restaurant.getId();
    }

    private boolean isValidRestaurant(RestaurantDTO restaurantDTO) {
        if (!StringUtils.hasText(restaurantDTO.getName())) {
            System.out.println("식당 이름이 비었음.");
            log.info("식당 이름이 비었음.");
            return false;
        }
        if (!StringUtils.hasText(restaurantDTO.getAddress())
                && restaurantDTO.getLatitude() == 0
                && restaurantDTO.getLongtitude() == 0) {
            log.info("식당 주소나 좌표가 비었음");
            return false;
        }
        return true;
    }


    public double[] addressToCoordinates(String street) {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder
                .fromUriString("https://naveropenapi.apigw.ntruss.com")
                .path("/map-geocode/v2/geocode")
                .queryParam("query", street)
                .encode()
                .build()
                .toUri();

        System.out.println("uri = " + uri);
        System.out.println("naverClientId = " + apiKey.getNaverClientId());
        System.out.println("naverClientSecret = " + apiKey.getNaverClientSecret());

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-NCP-APIGW-API-KEY-ID", apiKey.getNaverClientId())
                .header("X-NCP-APIGW-API-KEY", apiKey.getNaverClientSecret())
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);

        if (result.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("식당 주소를 확인하는데 실패하였습니다.");
        }

        System.out.println("result = " + result);

        return new double[]{};
    }
}
