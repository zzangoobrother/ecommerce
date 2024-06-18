package com.example.controller;

import com.example.application.MemberService;
import com.example.controller.dto.request.LoginMemberRequest;
import com.example.controller.dto.request.RegisterMemberRequest;
import com.example.repository.RedisRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final RedisRepository redisRepository;

    public MemberController(MemberService memberService, RedisRepository redisRepository) {
        this.memberService = memberService;
        this.redisRepository = redisRepository;
    }

    @PostMapping("/signup")
    public Long register(@Valid @RequestBody RegisterMemberRequest request) {
        return memberService.register(request.loginId(), request.password(), request.name());
    }

    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginMemberRequest loginMemberRequest, HttpServletRequest request) {
        String loginId = loginMemberRequest.loginId();
        memberService.login(loginId, loginMemberRequest.password());

        HttpSession session = request.getSession();
        session.setAttribute("memberId", loginId);
        session.setMaxInactiveInterval(3600);

        redisRepository.add(session.getId(), loginId);
    }
}
