package com.example.application;

import com.example.model.Member;
import com.example.repository.InMemoryMemberRepository;
import com.example.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberServiceTest {

    private MemberService memberService;
    private MemberRepository memberRepository;

    private final String loginId = "abcd";
    private final String password = "1234";
    private final String name = "홍길동";

    @BeforeEach
    void setUp() {
        memberRepository = new InMemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @Test
    void 회원가입() {
        memberService.register(loginId, password, name);

        Member member = memberRepository.getBy(loginId);

        assertAll(
                () -> assertThat(member.getLoginId()).isEqualTo(loginId),
                () -> assertThat(member.getName()).isEqualTo(name)
        );
    }

    @Test
    void 중복_아디가_존재하면_에러() {
        memberService.register(loginId, password, name);

        assertThatThrownBy(() -> memberService.register(loginId, password, name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 아이디가 존재합니다.");
    }

    @Test
    void 로그인() {
        memberService.register(loginId, password, name);

        Long memberId = memberService.login(loginId, password);

        Member member = memberRepository.getBy(loginId);

        assertThat(memberId).isEqualTo(member.getId());
    }

    @Test
    void 로그인시_비밀번호가_다르면_에러() {
        memberService.register(loginId, password, name);

        assertThatThrownBy(() -> memberService.login(loginId, "12"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디/비밀번호를 다시 입력해 주세요.");
    }
}
