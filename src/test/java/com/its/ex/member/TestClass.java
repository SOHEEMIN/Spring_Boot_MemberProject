package com.its.ex.member;

import com.its.ex.member.dto.MemberDTO;
import com.its.ex.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TestClass {
    @Autowired
    private MemberService memberService;

    //공통 메서드
    public MemberDTO newMember(int i){
        MemberDTO member = new MemberDTO("테스트용이메일"+i, "테스트용비밀번호"+i, "테스트용이름"+i, 99+i, "테스트용전화번호"+i);
        return member;
    }
    @Test
    @DisplayName("회원가입 테스트")
    @Transactional
    @Rollback(value = true)
    public void saveTest() {
        Long savedId = memberService.save(newMember(1));
        MemberDTO memberDTO = memberService.findById(savedId);
        assertThat(newMember(1).getMemberEmail()).isEqualTo(memberDTO.getMemberEmail());
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("로그인 테스트")
    public void loginTest(){
        final String memberEmail = "로그인용이메일"; // final: 변수명 변경 불가
        final String memberPassword = "로그인용 비밀번호";
        String memberName = "로그인용 이름";
        int memberAge = 99;
        String memberPhone = "로그인용 전화번호";
        MemberDTO memberDTO = new MemberDTO(memberEmail, memberPassword, memberName, memberAge, memberPhone);
        Long saveID = memberService.save(memberDTO);
        //로그인 객체 생성 후 로그인
        MemberDTO loginMemberDTO = new MemberDTO();
        loginMemberDTO.setMemberEmail(memberEmail);
        loginMemberDTO.setMemberPassword(memberPassword);
        MemberDTO loginResult = memberService.login(loginMemberDTO);
        //로그인 결과가 not null이면 테스트 통과
        assertThat(loginResult).isNotNull();
    }

    @Test
    @DisplayName("회원 데이터 저장")
    public void memberSave(){
        IntStream.rangeClosed(1, 20).forEach(i->{
            memberService.save(newMember(i));
        });
    }
}
