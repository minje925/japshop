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

    //== OrderItem 생성 메소드==//
    // Order가 생성될 때, OrderItem을 세팅하는 과정이다.
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOderPrice(orderPrice);
        orderItem.setCount(count);
        // orderitem 생성 및 세팅
        item.removeStock(count); // 상품의 재고감소
        return orderItem;
    }
    //==비즈니스 로직==//
    // 여러 개의 상품을 주문하고 취소했을 때, 각 상품의 재고를 늘려줘야 한다.
    public void cancel() {
        // 제고 수량을 원상복구 해준다.
        getItem().addstock(count);
    }
    // 주문가격*수량, 한 물품에 대한 전체 가격을 구하는 함수
    public int getTotalPrice() {
        return getOderPrice()*getCount(); // Getter, Setter가 설정되어 있어 get, set 가능함.
    }
}
