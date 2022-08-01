package com.jhyuk316.mapzip.service;

import com.jhyuk316.mapzip.dto.YoutuberDTO;
import com.jhyuk316.mapzip.model.RestaurantYoutuberEntity;
import com.jhyuk316.mapzip.model.YoutuberEntity;
import com.jhyuk316.mapzip.persistence.YoutuberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class YoutuberService {
    private final YoutuberRepository youtuberRepository;

    public Long save(YoutuberDTO youtuber) {
        // TODO - DTO 검증

        YoutuberEntity entity = YoutuberEntity.builder()
                .name(youtuber.getName())
                .channelId(youtuber.getChannelId())
                .build();

        youtuberRepository.save(entity);

        return entity.getId();
    }

    public YoutuberDTO getYoutuberWithRestaurant(Long id) {
        Optional<YoutuberEntity> optionalYoutuber = youtuberRepository.findByIdWithRestaurant(id);
        if (optionalYoutuber.isEmpty()) {
            throw new IllegalArgumentException("잘못된 유튜버_ID에요.");
        }

        YoutuberEntity youtuber = optionalYoutuber.get();

        for (RestaurantYoutuberEntity restaurantYoutuber : youtuber.getRestaurantYoutubers()) {
            System.out.println("restaurantYoutuber.getRestaurant().getName() = " + restaurantYoutuber.getRestaurant().getName());
        }

        return new YoutuberDTO(youtuber);
    }

}
