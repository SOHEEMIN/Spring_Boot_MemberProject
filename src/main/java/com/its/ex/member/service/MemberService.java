package com.its.ex.member.service;

import com.its.ex.member.dto.MemberDTO;
import com.its.ex.member.entity.MemberEntity;
import com.its.ex.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toEntity(memberDTO);
        Long id = memberRepository.save(memberEntity).getId();
        return id;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /**
         * login.html에서 이메일, 비번을 받아오고
         * DB로부터 해당 이메일의 정보를 가져와서
         * 입력받은 비번과 DB에서 조회한 비번의 일치여부를 판단하여
         * 일치하면 로그인 성공, 일치하지 않으면 로그인 실패로 처리**/
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(optionalMemberEntity.isPresent()){
            MemberEntity loginEntity = optionalMemberEntity.get();
            if(loginEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                return MemberDTO.toMemberDTO(loginEntity);
            }else {
                return null; //비밀번호 불일치
            }
        } else {
            return null; //해당 계정이 없음
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> entityList = memberRepository.findAll();
        List<MemberDTO>findList = new ArrayList<>();
        for(MemberEntity memberEntity: entityList) {
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            findList.add(memberDTO);
            //위의 두줄을 아래 한 줄로 줄여 쓸 수 있음.
            findList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return findList;
    }
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
        return;
    }
    public Long update(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toEntity(memberDTO);
        Long id = memberRepository.save(memberEntity).getId();
        return id;
    }
}