package jpabook.japshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@Slf4j  // 로그를 위한 롬복 이노테이션
public class HomeController {


    @RequestMapping("/")
    public String hone() {
        log.info("home controller");
        return "home";
    }
}
