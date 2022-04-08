package com.jhyuk316.mapzip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
    private String error;
    private List<T> data;
    private int size;
    private int current_page;
    private int last_page;
    private int from;
    private int to;
    private long total;
}
