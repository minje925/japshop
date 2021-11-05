package jpabook.japshop.Repository;

import jpabook.japshop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // 스프링 빈 자동 등록, 컴포넌트 스캔의 대상이므로 등록됨
public class MemberRepository {

    @PersistenceContext // EntityManager를 자동으로 주입해준다.
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // JPQL 문법은 SQL과 다름 => from의 대상이 테이블이 아닌 객체를 대상으로 쿼리를 한다.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
