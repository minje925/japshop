package jpabook.japshop.domain;

import jpabook.japshop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name="order_id")    // Order 테이블과의 join
    private Order order;

    private int oderPrice;   // 주문 가격,(주문당시)
    private int count;  // 주문 수량
}
