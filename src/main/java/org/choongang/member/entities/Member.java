package org.choongang.member.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.choongang.commons.entities.Base;

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
}
