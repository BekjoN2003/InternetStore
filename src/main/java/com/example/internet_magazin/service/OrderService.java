package com.example.internet_magazin.service;

import com.example.internet_magazin.dto.order.OrderCreateDto;
import com.example.internet_magazin.dto.order.OrderDto;
import com.example.internet_magazin.entity.Order;
import com.example.internet_magazin.exception.BadRequest;
import com.example.internet_magazin.repository.OrderRepository;
import com.example.internet_magazin.type.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getEntity(Integer id) {
        Optional<Order> optional = orderRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BadRequest("Order not found");
        }
        return optional.get();
    }

    public String create(Integer profile_id, OrderCreateDto dto) {
        Order order = new Order();
        order.setProfileId(profile_id);
        order.setRequirement(dto.getRequirement());
        order.setCreatedAt(LocalDateTime.now());
        order.setPaymentType(dto.getPaymentType());
        order.setContact(dto.getContact());
        order.setStatus(OrderStatus.CREATED);
        order.setAddress(dto.getAddress());
        orderRepository.save(order);
        return "Order created !";
    }

    public OrderDto get(Integer id) {
        return convertToDto(getEntity(id), new OrderDto());
    }

    public List<OrderDto> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findAll(pageRequest);
        List<OrderDto> dtoList = new ArrayList<>();
        for (Order o : orderPage) {
            dtoList.add(convertToDto(o, new OrderDto()));
        }
        return dtoList;
    }

    public boolean delete(Integer id) {
        Order order = getEntity(id);
        order.setDeletedAt(LocalDateTime.now());
        orderRepository.delete(order);
        return true;
    }

    public OrderDto payment(Integer id) {
        Order order = getEntity(id);
        if (order.getStatus().equals(OrderStatus.CREATED)) {
            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);
            return convertToDto(order, new OrderDto());
        } else if (order.getStatus().equals(OrderStatus.DELIVERED)) {
            throw new BadRequest("This order is delivered! id:" + id);
        } else throw new BadRequest("Order is not been paid! id: " + id);
    }

    public OrderDto convertToDto(Order order, OrderDto dto) {
        dto.setAddress(order.getAddress());
        dto.setContact(order.getContact());
        dto.setRequirement(order.getRequirement());
        return dto;
    }

    public OrderDto deliveryDate(Integer id, LocalDate date) {
        Order order = getEntity(id);
        if (order.getDeliveryDate() != null) {
            throw new BadRequest("This Order is delivered" + id);
        }
        order.setDeliveryDate(LocalDateTime.from(date));
        orderRepository.save(order);
        return convertToDto(order, new OrderDto());
    }

    public OrderDto deliveredAt(Integer id) {
        Order order = getEntity(id);
        if (order.getStatus().equals(OrderStatus.DELIVERED)) {
            throw new BadRequest("This order is delivered! ID: " + id);
        } else if (order.getStatus().equals((OrderStatus.CREATED))) {
            throw new BadRequest("This order has not yet been paid! ID: " + id);
        }
        order.setDeliveryAt(LocalDateTime.now());
        order.setStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);
        return convertToDto(order, new OrderDto());

    }
}
