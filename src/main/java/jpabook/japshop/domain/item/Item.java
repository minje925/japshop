package jpabook.japshop.domain.item;

import jpabook.japshop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 물품 종류에 대한 테이블 생성 전략이다.
// 싱글테이블 기반으로 하나의 테이블에 Album, Book, Movie를 담을 것이다.
@DiscriminatorColumn(name = "dtype") // Album, Book, Movie를 구분하는 타입 컬럼
public abstract class Item {
    // 이 클래스는 하위 클래스가 있기 때문에 하위클래스에서 상속받아 사용할 것이다.
    // 따라서 추상클래스로 생성한다.
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
