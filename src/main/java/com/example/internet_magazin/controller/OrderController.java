package com.example.internet_magazin.controller;

import com.example.internet_magazin.dto.order.OrderCreateDto;
import com.example.internet_magazin.dto.order.OrderDto;
import com.example.internet_magazin.entity.Product;
import com.example.internet_magazin.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestParam("product_id")Integer product_id,
                                         @RequestParam("profile_id") Integer profile_id,
                                         @RequestParam @Valid OrderCreateDto dto) {
        String result = orderService.create(product_id, profile_id, dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable("id") Integer id) {
        OrderDto result = orderService.get(id);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/deliveryDate/{id}")
    public ResponseEntity<?> deliveryDate(@PathVariable("id") Integer id, LocalDate date){
        OrderDto result = orderService.deliveryDate(id, date);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/deliveredAt/{id}")
    public ResponseEntity<?> delivery(@PathVariable("id") Integer id){
        OrderDto result = orderService.deliveredAt(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/payment/{id}")
    public ResponseEntity<?> payment(@PathVariable("id") Integer id){
        OrderDto result = orderService.payment(id);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllOrder(@RequestParam("page") Integer page,
                                         @RequestParam("size") Integer size) {
        List<OrderDto> result = orderService.getAll(page, size);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Integer id) {
        boolean result = orderService.delete(id);
        return ResponseEntity.ok(result);
    }
}