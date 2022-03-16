package com.jhyuk316.mapzip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.jhyuk316.mapzip.model.*;
import com.jhyuk316.mapzip.persistence.*;

@Slf4j
@Service
public class MapzipService {
    @Autowired
    private MapzipRepository repository;

    public String testService() {
        MapzipEntity entity = MapzipEntity.builder().restName("first rest name").build();
        repository.save(entity);
        MapzipEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getRestName();
    }



}
