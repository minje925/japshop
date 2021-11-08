package jpabook.japshop.service;

import jpabook.japshop.Repository.ItemRepository;
import jpabook.japshop.Repository.MemberRepository;
import jpabook.japshop.Repository.OrderRepository;
import jpabook.japshop.Repository.OrderSearch;
import jpabook.japshop.domain.Delivery;
import jpabook.japshop.domain.Member;
import jpabook.japshop.domain.Order;
import jpabook.japshop.domain.OrderItem;
import jpabook.japshop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);
        // Delivery를 객체로 따로 생성한 이유 =>
        // order가 persist될 때, cascade.ALL로 설정된 테이블도 자동으로 persist해준다.
        System.out.println("주문은 완료되었습니다.");
        return order.getId();
    }

    @Transactional
    // 취소
    public void cancelOrder(Long orderId) {
        // 주문 찾기
        Order order = orderRepository.findOne(orderId);
        // 주문 췩소
        order.cancel();
    }

    // 검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }

}
