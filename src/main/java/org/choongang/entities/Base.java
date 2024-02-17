package org.choongang.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

//공통으로 사용할 속성
@Getter @Setter
@MappedSuperclass //JPA에서 엔티티로 사용 될 자식클래스의 부모클래스로 작용
@EntityListeners(AuditingEntityListener.class) //엔티티의 변경사항 감지(생성일자, 수정일자 등) -> 설정활성화 필요
public abstract class Base {
    @CreatedDate
    @Column(updatable = false) //수정불가하도록 설정
    private LocalDateTime createdAt; //생성일자

    @LastModifiedDate
    @Column(insertable = false) //추가할 때에는 바뀌지 않도록 설정
    private LocalDateTime modifiedAt; //수정날짜
}
