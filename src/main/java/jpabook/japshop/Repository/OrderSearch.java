package jpabook.japshop.Repository;

import jpabook.japshop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    private String memberName; // 검색 조건이 회원이름
    private OrderStatus orderStatus; // 검색 조건이 주문상태
}
