package com.example.controller;

import com.example.application.MemberService;
import com.example.controller.dto.request.RegisterMemberRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public Long register(@RequestBody RegisterMemberRequest request) {
        return memberService.register(request.name());
    }
}
