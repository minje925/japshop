package jpabook.japshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 생성할 테이블명을 지정해준다. 지정하지 않으면 클래스명으로 테이블명이 생성된다.
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne // order입장에서는 다대일 관계
    @JoinColumn(name="member_id")   // FK를 지정하여 member 테이블과 join, 이렇게 지정하면 FK값이 바뀌면 Member의 값도 변경된다.
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // CasecadeType.ALL을 지정하면 조인된 테이블도 persist를 해준다.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;    // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL], enum 타입

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 주문 생성 메소드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();  // 주문 객체 생성
        order.setMember(member);    // 주문회원 세팅
        order.setDelivery(delivery);    // 배달정보 세팅
        for(OrderItem orderItem: orderItems) {  // 주문 물건리스트 세팅
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER); // 주문상태 세팅
        order.setOrderDate(LocalDateTime.now());    // 주문시간 세팅(현재 시간)
        return order;
    }

    //==비즈니스 로직==//
    // 주문취소
    public void cancel() {
        // 이미 배송중인 경우 취소불가능하다.
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem: this.orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    // 전체 주문 가격을 구해 조회하는 로직
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem: orderItems) {
            totalPrice+=orderItem.getTotalPrice();
            // 주문할 때 한 상품의 가격은 (가격*수량) 이기 때문에 getTotalPrice() 함수를 또 정의해야 한다.
        }
        return totalPrice;
    }
}
