package com.jhyuk316.mapzip.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jhyuk316.mapzip.ApiKey;
import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.model.CategoryEntity;
import com.jhyuk316.mapzip.model.RestaurantCategoryEntity;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.persistence.CategoryRepository;
import com.jhyuk316.mapzip.persistence.RestaurantCategoryRepository;
import com.jhyuk316.mapzip.persistence.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final RestaurantCategoryRepository restaurantCategoryRepository;
    private final ApiKey apiKey;

    @Transactional
    public Long save(RestaurantDTO restaurantDTO) {
        if (!isValidRestaurant(restaurantDTO)) {
            throw new IllegalArgumentException("올바르지 않는 식당 정보입니다.");
        }

        log.debug("Trying  to save restaurantDTO.getName() = " + restaurantDTO.getName());
        log.debug("Trying  to save restaurantDTO.getAddress() = " + restaurantDTO.getAddress());

        Address address = addressToCoordinates(restaurantDTO.getAddress());

        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(restaurantDTO.getName())
                .address(address.getRoad())
                .longitude(address.getLongitude())
                .latitude(address.getLatitude())
                .build();

        restaurantRepository.save(restaurant);

        log.info("Restaurant id: {}, name: {} 으로 저장했어요.", restaurant.getId(), restaurant.getName());

        return restaurant.getId();
    }

    private boolean isDuplicateRestaurant(RestaurantDTO restaurantDTO) {
        return false;
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

    static class Address {
        private final String road; // 도로명 주소
        private final double longitude;
        private final double latitude;

        public Address(String road, double longitude, double latitude) {
            this.road = road;
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public String getRoad() {
            return road;
        }

        public double getLongitude() {
            return longitude;
        }

        public double getLatitude() {
            return latitude;
        }
    }

    public Address addressToCoordinates(String street) {
        String NaverApiUrl = "https://naveropenapi.apigw.ntruss.com";
        WebClient webClient = WebClient.create(NaverApiUrl);

        Mono<String> mono = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/map-geocode/v2/geocode")
                        .queryParam("query", street)
                        .build())
                .header("X-NCP-APIGW-API-KEY-ID", apiKey.getNaverClientId())
                .header("X-NCP-APIGW-API-KEY", apiKey.getNaverClientSecret())
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));

        String block = mono.block();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(block, JsonObject.class);
        System.out.println("jsonObject = " + jsonObject);

        JsonArray addresses = jsonObject.get("addresses").getAsJsonArray();
        System.out.println("addresses = " + addresses);

        if (addresses.size() != 1) {
            throw new IllegalArgumentException("잘못된 주소에요. 정확한 주소를 주세요.");
        }

        JsonObject address = addresses.get(0).getAsJsonObject();

        String road = address.get("roadAddress").getAsString();
        double longitude = address.get("x").getAsDouble();
        double latitude = address.get("y").getAsDouble();

        return new Address(road, longitude, latitude);
    }

    @Transactional
    public void addCategory(Long restaurantId, String category) {
        Optional<RestaurantEntity> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            throw new IllegalArgumentException("잘못된 식당 정보입니다.");
        }
        RestaurantEntity restaurant = optionalRestaurant.get();

        CategoryEntity categoryEntity = categoryRepository.findByName(category).orElseGet(() -> new CategoryEntity(category));
        categoryRepository.save(categoryEntity);

        if (restaurantCategoryRepository.findByRestaurantAndCategory(restaurant, categoryEntity).isPresent()) {
            throw new IllegalArgumentException("카테고리 중복, 이미 있는 카테고리에요.");
        }

        RestaurantCategoryEntity restaurantCategoryEntity = new RestaurantCategoryEntity(restaurant, categoryEntity);
        restaurantCategoryRepository.save(restaurantCategoryEntity);

        // 관계 설정
        restaurant.addRestaurantCategory(restaurantCategoryEntity);
        categoryEntity.addRestaurantCategory(restaurantCategoryEntity);
    }

    public RestaurantDTO getCategories(Long restaurantId) {
        Optional<RestaurantEntity> optionalRestaurant = restaurantRepository.findByIdWithCategory(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            throw new IllegalArgumentException("잘못된 식당 정보");
        }
        RestaurantEntity restaurant = optionalRestaurant.get();

        RestaurantDTO restaurantDTO = new RestaurantDTO(restaurant);
        for (RestaurantCategoryEntity entity : restaurant.getRestaurantCategories()) {
            restaurantDTO.getCategories().add(entity.getCategory().getName());
        }

        return restaurantDTO;
    }

    public RestaurantDTO getRestaurant(long id) {
        RestaurantEntity entity = restaurantRepository.getById(id);
        return new RestaurantDTO(entity);
    }

}
