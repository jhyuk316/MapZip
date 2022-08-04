package com.jhyuk316.mapzip.api;

import com.jhyuk316.mapzip.dto.CategoryDTO;
import com.jhyuk316.mapzip.dto.ResponseDTO;
import com.jhyuk316.mapzip.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<ResponseDTO<CategoryDTO>> getCategories() {
        List<CategoryDTO> categories = categoryService.getCategories();

        ResponseDTO<CategoryDTO> responseDTO = ResponseDTO.<CategoryDTO>builder()
                .result(categories)
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<ResponseDTO<CategoryDTO>> getCategories(@PathVariable Long id) {
        CategoryDTO categoryDTO = categoryService.getRestaurants(id);

        ResponseDTO<CategoryDTO> responseDTO = ResponseDTO.<CategoryDTO>builder()
                .result(List.of(categoryDTO))
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping(value = "/categories", params = {"name"})
    public ResponseEntity<ResponseDTO<CategoryDTO>> getCategories(@RequestParam("name") String name) {
        CategoryDTO categoryDTO = categoryService.getRestaurants(name);

        ResponseDTO<CategoryDTO> responseDTO = ResponseDTO.<CategoryDTO>builder()
                .result(List.of(categoryDTO))
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }
}
