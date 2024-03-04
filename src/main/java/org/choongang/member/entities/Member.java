package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.choongang.commons.entities.Base;

import java.util.ArrayList;
import java.util.List;

//회원테이블
@Data
@Entity
public class Member extends Base {
    @Id @GeneratedValue //자동증감 시퀀스
    private Long seq;

    @Column (length =80, nullable = false, unique = true)
    private String email;

    @Column(length=40, nullable = false, unique = true)
    private String userId;

    @Column(length=65, nullable = false)
    private String password;

    @Column(length=40, nullable = false)
    private String name;

    @ToString.Exclude //순환 참조 방지 - 해당필드를 포함하지 않도록
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    //mappedBy: 연관관계 주인 명시, fetch:  필요할 때 조회할 수 있도록 Lazy?
    private List<Authorities> authorities = new ArrayList<>(); //회원에서 권한 조회


}
