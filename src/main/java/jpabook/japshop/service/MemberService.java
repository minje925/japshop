package jpabook.japshop.service;

import jpabook.japshop.Repository.MemberRepository;
import jpabook.japshop.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service    // 스프링 빈에 자동 등록
//@Transactional  // 트랜잭션 단위로 수행함
public class MemberService {

    @Autowired  // injection
    private MemberRepository memberRepository;
    /*
    // 생성자 인젝션 방법
    private MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    */
    // 회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 중복회원 검사
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 전체 회원 조회
    @Transactional(readOnly = true) // read 쿼리만 하는 경우에 이 어노테이션을 추가하면 효율이 증가
    public List<Member> findMembers()  {
        return memberRepository.findAll();
    }
    // 한명 조회
    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
