package com.jhyuk316.mapzip.api;

import com.jhyuk316.mapzip.dto.ResponseDTO;
import com.jhyuk316.mapzip.dto.YoutuberDTO;
import com.jhyuk316.mapzip.service.YoutuberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class YoutuberApiController {
    private final YoutuberService youtuberService;

    @GetMapping("/youtubers")
    public ResponseEntity<ResponseDTO<YoutuberDTO>> getYoutubers() {
        List<YoutuberDTO> youtubers = youtuberService.getYoutubers();
        ResponseDTO<YoutuberDTO> responseDTO = ResponseDTO.<YoutuberDTO>builder()
                .result(youtubers)
                .build();

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/youtubers/{id}")
    public ResponseEntity<ResponseDTO<YoutuberDTO>> getYoutubers(@PathVariable("id") Long id) {
        YoutuberDTO youtuber = youtuberService.getYoutuberWithRestaurant(id);

        ResponseDTO<YoutuberDTO> responseDTO = ResponseDTO.<YoutuberDTO>builder()
                .result(List.of(youtuber))
                .build();

        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/youtubers")
    public ResponseEntity<ResponseDTO<Long>> addYoutubers(@RequestBody @Valid YoutuberDTO youtuberDTO) {
        Long savedId = youtuberService.save(youtuberDTO);

        ResponseDTO<Long> responseDTO = ResponseDTO.<Long>builder()
                .result(List.of(savedId))
                .build();

        return ResponseEntity.ok().body(responseDTO);
    }


}
