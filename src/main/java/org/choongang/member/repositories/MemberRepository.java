package org.choongang.member.repositories;

import org.choongang.member.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

//추가적인 조건이 많을 때에는 QuerydslPredicateExecutor 추가
public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
    /**
     * 유연하게 쓰기 위해 optional 형태 사용
     * 해당 ID나 이메일에 해당하는 Member가 없는 경우
     * null이 아닌 Optional.empty()를 반환하여
     * NullPointerException 등의 예외를 방지할 수 있다.
     */
    Optional<Member> findByEmail(String email);
    Optional<Member> findByUserId(String userId);
}
