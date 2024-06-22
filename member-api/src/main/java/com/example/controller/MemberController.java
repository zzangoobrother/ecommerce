package com.example.controller;

import com.example.application.MemberService;
import com.example.controller.dto.request.LoginMemberRequest;
import com.example.controller.dto.request.RegisterMemberRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public Long register(@Valid @RequestBody RegisterMemberRequest request) {
        return memberService.register(request.loginId(), request.password(), request.name());
    }

    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginMemberRequest loginMemberRequest, HttpServletRequest request) {
        String loginId = loginMemberRequest.loginId();
        Long memberId = memberService.login(loginId, loginMemberRequest.password());
        System.out.println("dssds");
        System.out.println(memberId);
        HttpSession session = request.getSession();
        session.setAttribute("memberId", memberId);
        session.setMaxInactiveInterval(3600);
    }
}
