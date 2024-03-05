package org.choongang.commons.entities;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

//회원정보가 들어가있는 공통 속성 + 파일 기록날짜
@Getter @Setter
//엔티티가 추가/수정될 때 이벤트 감지
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseMember extends Base {

    //처음 추가
    @CreatedBy  //자동으로 들어감
    @Column(length=40, updatable = false)
    private String createdBy;

    //수정
    @LastModifiedBy
    @Column(length=40, insertable = false)
    private String modifiedBy;
}
