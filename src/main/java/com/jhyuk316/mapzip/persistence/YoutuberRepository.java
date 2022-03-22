package com.jhyuk316.mapzip.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.jhyuk316.mapzip.model.YoutuberEntity;

@Repository
public interface YoutuberRepository extends JpaRepository<YoutuberEntity, Integer> {
    List<YoutuberEntity> findByName(String name);

    List<YoutuberEntity> findByNameContains(String name);
}
