package com.example.internet_magazin.controller;

import com.example.internet_magazin.dto.orderItm.OrderItemCreateDto;
import com.example.internet_magazin.dto.orderItm.OrderItemDto;
import com.example.internet_magazin.service.OrderItemService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController (OrderItemService orderItemService){
        this.orderItemService = orderItemService;
    }

    @PostMapping()
    public ResponseEntity<?> createOrder(@RequestParam("orderId")Integer order,@RequestBody @Valid OrderItemCreateDto dto){
        OrderItemDto result = orderItemService.create(order,dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@PathVariable("id") Integer id) {
        OrderItemDto result = orderItemService.get(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(@RequestParam("page")Integer page,
                                    @RequestParam("size")Integer size){
        List<OrderItemDto> result= orderItemService.getAll(page,size);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateItem(@PathVariable("id") Integer id, @Valid OrderItemCreateDto dto){
        OrderItemDto result = orderItemService.update(id, dto);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable("id") Integer id){
        boolean result = orderItemService.delete(id);
        return ResponseEntity.ok(result);
    }



}
