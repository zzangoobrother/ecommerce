package com.example.controller;

import com.example.application.CouponService;
import com.example.controller.dto.CreateCouponRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/coupons")
@RestController
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String create(@RequestBody CreateCouponRequest request) {
        return couponService.create(request.count());
    }
}
