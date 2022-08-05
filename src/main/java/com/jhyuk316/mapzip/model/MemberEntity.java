package com.jhyuk316.mapzip.model;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


// TODO 회원 기능은 보류
@Getter
// @Entity
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotNull
    private String name;

    @OneToMany()
    private List<YoutuberEntity> youtubers = new ArrayList<>();

}
