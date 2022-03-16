package com.jhyuk316.mapzip.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.jhyuk316.mapzip.model.MapzipEntity;

@Repository
public interface MapzipRepository extends JpaRepository<MapzipEntity, String> {
    List<MapzipEntity> findByRestName(String restName);
}
