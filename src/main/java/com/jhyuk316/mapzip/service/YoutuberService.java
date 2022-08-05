package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.dto.RestaurantDTO;
import com.jhyuk316.mapzip.dto.YoutuberDTO;
import com.jhyuk316.mapzip.model.RestaurantEntity;
import com.jhyuk316.mapzip.model.RestaurantYoutuberEntity;
import com.jhyuk316.mapzip.model.YoutuberEntity;
import com.jhyuk316.mapzip.persistence.YoutuberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Validated
@Transactional(readOnly = true)
@Service
public class YoutuberService {
    private final YoutuberRepository youtuberRepository;

    public Long save(@Valid YoutuberDTO youtuber) {
        if (isDuplicateYoutuber(youtuber)) {
            throw new IllegalArgumentException("이미 있는 유튜버에요.");
        }

        YoutuberEntity entity = YoutuberEntity.builder()
                .name(youtuber.getName())
                .channelId(youtuber.getChannelId())
                .build();

        youtuberRepository.save(entity);

        return entity.getId();
    }

    private boolean isDuplicateYoutuber(YoutuberDTO youtuber) {
        return youtuberRepository.findByChannelId(youtuber.getChannelId()).isPresent();
    }

    public List<YoutuberDTO> getYoutubers() {
        List<YoutuberEntity> youtubers = youtuberRepository.findAll();
        return youtubers.stream().map(YoutuberDTO::new).toList();
    }

    public YoutuberDTO getYoutuberWithRestaurant(Long id) {
        YoutuberEntity youtuber = youtuberRepository.findByIdWithRestaurant(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 유튜버_ID에요."));

        YoutuberDTO youtuberDTO = new YoutuberDTO(youtuber);
        for (RestaurantYoutuberEntity restaurantYoutuber : youtuber.getRestaurantYoutubers()) {
            RestaurantEntity restaurant = restaurantYoutuber.getRestaurant();
            YoutuberDTO.InnerRestaurantDTO innerRestaurantDTO = new YoutuberDTO.InnerRestaurantDTO(restaurant);
            innerRestaurantDTO.setVideoId(restaurantYoutuber.getVideoId());
            youtuberDTO.getRestaurants().add(innerRestaurantDTO);
        }

        return youtuberDTO;
    }

}
