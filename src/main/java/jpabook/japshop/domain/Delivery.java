package jpabook.japshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")   // 일대일 관계의 경우 FK를 둘 테이블을 정해야 한다. 예제에서는 Order에 둘 것이다.
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // enum 타입은 숫자나 문자열을 선택할 수 있음 => STRING타입으로 선택함
    private DeliveryStatus status; // enum타입으로 READY, COMP 준비와 배송중으로 나뉨


}
