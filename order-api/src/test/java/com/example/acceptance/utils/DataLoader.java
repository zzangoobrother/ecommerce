package com.example.acceptance.utils;

import com.example.model.Member;
import com.example.repository.MemberRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader {

    private MemberRepository memberRepository;
    private ProductRepository productRepository;

    public DataLoader(MemberRepository memberRepository, ProductRepository productRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public void loadData() {
        memberRepository.save(
                Member.builder()
                        .loginId("abcde")
                        .password("1234")
                        .name("홍길동")
                        .build()
        );

        productRepository.save(
                Product.builder()
                        .name("빈티지 티")
                        .price(new BigDecimal("1000"))
                        .quantity(100)
                        .build()
        );
    }
}
