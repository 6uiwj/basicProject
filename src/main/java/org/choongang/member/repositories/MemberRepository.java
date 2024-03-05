package org.choongang.member.repositories;

import org.choongang.member.entities.Member;
import org.choongang.member.entities.QMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

//추가적인 조건이 많을 때에는 QuerydslPredicateExecutor 추가
//JpaRepository : find메서드
//Querydsl : where 조건(find메서드에 매개변수로 predicate(다양한 조건식을 쓸 수 있게 해줌)가 들어감)
public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
    /**
     * 유연하게 쓰기 위해 optional 형태 사용
     * 해당 ID나 이메일에 해당하는 Member가 없는 경우
     * null이 아닌 Optional.empty()를 반환하여
     * NullPointerException 등의 예외를 방지할 수 있다.
     */
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findByUserId(String userId);


    /**
     *
     * @param email
     * @return predicate의 구현체
     */
    //이메일로 회원 존재여부 체크 ( 조건식 사용 - QuerydslExecutor 필요)
    default boolean existsByEmail(String email) {
        //Q형태클래스에 이미 항목들이 존재하므로
        QMember member = QMember.member;
        return exists(member.email.eq(email));
    }

    default boolean existsByUserId(String userId) {
        QMember member = QMember.member;
        return exists(member.userId.eq(userId));
    }
}
