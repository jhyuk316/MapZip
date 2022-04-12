package com.jhyuk316.mapzip.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
    private String error_message;
    private int page;
    private List<T> result;
    private long total_result;
    private int total_pages;
}
