package jpabook.japshop.service;


import jpabook.japshop.Repository.ItemRepository;
import jpabook.japshop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional // 클래스 전체는 readOnly이므로 save만 풀어준다.
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    /**
     * 영속성 컨텍스트가 자동 변경
     */
    @Transactional
    public void updateItem(Long id, String name, int price) {
        // 변경 감지 방법!
        Item item = itemRepository.findOne(id);
        item.setName(name);
        item.setPrice(price);
    }
}
