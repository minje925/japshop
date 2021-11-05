package jpabook.japshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable // Address는 Jpa의 내장 타입이기 때문에 이 어노테이션을 추가(어딘가에 내장될 수 있음)
@Getter @Setter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
