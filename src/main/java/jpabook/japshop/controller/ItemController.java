package jpabook.japshop.controller;

import jpabook.japshop.domain.item.Book;
import jpabook.japshop.domain.item.Item;
import jpabook.japshop.service.ItemService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items"; // 상품 목록으로 가버리기
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "/items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn((item.getIsbn()));

        model.addAttribute("form",form);
        return "items/updateItemForm";
    }
    @PostMapping("items/{itemId}/edit")
    public String updateItemForm(@ModelAttribute("form") BookForm form) {
        // @ModelAttribute("form") form으로 넘어온 데이터임
        /*
        // 병함 방법, 컨틀롤러에서 엔티티 객체를 생성하는건 좋지 않다.
        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        itemService.saveItem(book);
        */
        itemService.updateItem(form.getId(), form.getName(), form.getPrice());

        return "redirect:/items";
    }
}
