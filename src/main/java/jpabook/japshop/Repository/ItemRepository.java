package jpabook.japshop.Repository;

import jpabook.japshop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor // 생성자 주입
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if(item.getId() == null) {  // 상품목록에 상품이 없을 때, (처음 넣을 때)
            em.persist(item);
        } else {
            em.merge(item); // 이미 상품이 있을 때, (update)
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
