package com.jhyuk316.mapzip.controller;

import com.jhyuk316.mapzip.dto.MemberDTO;
import com.jhyuk316.mapzip.dto.ResponseDTO;
import com.jhyuk316.mapzip.service.MemberService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/{id}")
    public ResponseEntity<?> getMembers(@PathVariable(required = true) long id) {
        // TODO
        return null;
    }

    @PostMapping("/members")
    public ResponseEntity<?> addMembers(@Valid @RequestBody MemberDTO memberDTO) {
        MemberDTO result = memberService.saveMember(memberDTO);

        ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder()
            .result((List<MemberDTO>) result)
            .build();

        return ResponseEntity.ok().body(response);
    }


}
