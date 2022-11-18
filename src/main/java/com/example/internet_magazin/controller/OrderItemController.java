package com.example.internet_magazin.controller;

import com.example.internet_magazin.dto.image.ImageDto;
import com.example.internet_magazin.dto.order.OrderDto;
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
        OrderDto result = orderItemService.create(dto);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/")
    public ResponseEntity<?> getOrder(@RequestBody @Valid ImageDto dto){
        OrderDto result = orderItemService.get(dto);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/")
    public ResponseEntity<?> updateOrder(@RequestBody @Valid ImageDto dto){
        OrderDto result = orderItemService.update(dto);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/")
    public ResponseEntity<?> deleteOrder(@RequestBody @Valid ImageDto dto){
        boolean result = orderItemService.delete(dto);
        return ResponseEntity.ok(result);
    }

}
