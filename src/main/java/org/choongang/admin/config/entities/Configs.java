package org.choongang.admin.config.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

//코드와 데이터 두가지 형태로
@Data
@Entity

public class Configs {
    @Id
    @Column(length=60)
    private String code; //기본키
    @Lob
    private String data; //JSON형태

}
