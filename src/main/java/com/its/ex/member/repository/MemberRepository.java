package com.its.ex.member.repository;

import com.its.ex.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // select * from member_table where memberEmail = ?
    // 리턴타입 : MemberEntity / 매개변수: memberEmail(String)
    // 추상메서드: 메서드의 형태만 정의할 뿐, 실행블록을 가질 수 없다.
    Optional<MemberEntity> findByMemberEmail(String memberEmail);

}
