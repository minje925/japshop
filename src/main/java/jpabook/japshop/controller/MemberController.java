package jpabook.japshop.controller;

import jpabook.japshop.domain.Address;
import jpabook.japshop.domain.Member;
import jpabook.japshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") // Post 방식으로 Form을 가져옴
    public String create(@Valid MemberForm form, BindingResult result) {

        if(result.hasErrors()) {
            return "members/createMemberForm"; // 에러에 관한 정보는 result에 담겨있음
            // 에러가 발생하면 회원가입페이지 새로 호출
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        try {
            // 중복에 대한 에러처리
            memberService.join(member);
        } catch (IllegalStateException e) {
            result.addError(new FieldError("memberForm", "name", e.getMessage()));
            return "members/createMemberForm";
        }
        return "redirect:/"; // 첫번째 페이지로 넘어간다.
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
