package jpabook.japshop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class) // 스프링과 관련된 것을 테스트하기 위해 적어줌
@SpringBootTest              // 스프링 부트로 테스트한다.
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository; // 객체 주입받고

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("ChoiMinje");

        // when
        Long result = memberRepository.save(member);
        Member findMember = memberRepository.find(result);
        // then
        org.assertj.core.api.Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        org.assertj.core.api.Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    }
}