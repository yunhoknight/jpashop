package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//@AllArgsConstructor // (롬복) 모든 생성자 생성
@RequiredArgsConstructor // final 필드로만 생성자 생성
public class MemberService {

//    @Autowired // 필드 주입
    private final MemberRepository memberRepository;

//    @Autowired // 세터 주입
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }


//    @Autowired // 생성자 주입
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 회원가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 회원 중복 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findMember(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
