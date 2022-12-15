package com.example.internet_magazin.service;

import com.example.internet_magazin.dto.orderItm.OrderItemCreateDto;
import com.example.internet_magazin.dto.orderItm.OrderItemDto;
import com.example.internet_magazin.entity.OrderItem;
import com.example.internet_magazin.exception.BadRequest;
import com.example.internet_magazin.repository.OrderItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem getEntity(Integer id) {
        Optional<OrderItem> optional = orderItemRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BadRequest("Order Item not found !!");
        }
        return optional.get();
    }

    public OrderItemDto get(Integer id) {
        return convertToDto(getEntity(id), new OrderItemDto());
    }

    public OrderItemDto create(Integer orderId, OrderItemCreateDto dto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setCreatedAt(LocalDateTime.now());
        orderItem.setProductId(dto.getProductId());
        orderItem.setPrice(dto.getProductDto().getPrice());
        orderItem.setAmount(dto.getAmount());
        orderItemRepository.save(orderItem);
        return convertToDto(orderItem, new OrderItemDto());
    }

    public OrderItemDto update(Integer id, OrderItemCreateDto dto) {
        OrderItem orderItem = getEntity(id);
        orderItem.setUpdatedAt(LocalDateTime.now());
        orderItem.setPrice(dto.getProductDto().getPrice());
        orderItem.setAmount(dto.getAmount());
        orderItemRepository.save(orderItem);
        return convertToDto(orderItem, new OrderItemDto());
    }

    public boolean delete(Integer id) {
        OrderItem orderItem = getEntity(id);
        orderItem.setDeletedAt(LocalDateTime.now());
        orderItemRepository.save(orderItem);
        return true;
    }


    public List<OrderItemDto> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<OrderItem> orderItems = orderItemRepository.findAll(pageRequest);
        return orderItems.stream().map(orderItem -> convertToDto(orderItem,
                new OrderItemDto())).collect(Collectors.toList());
    }

    public OrderItemDto convertToDto(OrderItem item, OrderItemDto dto) {
        dto.setId(item.getId());
        dto.setOrderId(item.getOrderId());
        dto.setAmount(item.getAmount());
        dto.setPrice(item.getPrice());
        return dto;
    }
}
