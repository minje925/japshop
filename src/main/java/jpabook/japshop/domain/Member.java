package jpabook.japshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id") // id는 실제 테이블의 컬럼에 member_id로 정의되도록 함
    private Long id;    // PK, member_id

    private String name;    // 회원 이름

    @Embedded                   // 내장 타입이므로 매핑해준다.
    private Address address;    // 회원 주소

    @OneToMany(mappedBy = "member") // order와 일대다 관계, 한 사람이 여러 개의 주문을 할 수 있으므로,
    //mappedBy를 하면 주인이 아님(읽기 전용) 여기서 "member"는 order의 member를 가르킴
    private List<Order> orders = new ArrayList<>(); // order테이블과의 일대다이므로 리스트를 가짐
}
