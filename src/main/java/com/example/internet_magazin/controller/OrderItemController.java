package com.example.internet_magazin.controller;

import com.example.internet_magazin.dto.image.ImageDto;
import com.example.internet_magazin.dto.order.OrderDto;
import com.example.internet_magazin.dto.orderItm.OrderItemDto;
import com.example.internet_magazin.service.OrderItemService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/item")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController (OrderItemService orderItemService){
        this.orderItemService = orderItemService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDto dto){
        OrderItemDto result = orderItemService.create(dto);
        return ResponseEntity.ok(result);
    }


}
