package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.member.Authority;

//권한은 상수로 설정
@Data
@Entity
//회원 한명한테 동일한 권한을 여러개 부여할 수도 있으므로 권한과 멤버를 묶어서 unique 속성을 부여하자.
@Table(indexes = @Index(name="uq_member_authority", columnList = "member_seq, authority",
unique = true))
public class Authorities {
    @Id
    @GeneratedValue
    private Long seq; //기본키

    //한 멤버가 여러가지의 권한을 받을 수 있다. -> ManyToOne관계(One:회원 - Many:권한)
    // -> Authorities 쪽이 외래키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "member_seq")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(length=15, nullable = false)
    private Authority authority;

}
