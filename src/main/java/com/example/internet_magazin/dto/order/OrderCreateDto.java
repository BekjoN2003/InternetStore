package com.example.internet_magazin.dto.order;

import com.example.internet_magazin.type.PaymentType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderCreateDto {
    private Integer id;
    private String requirement;
    @NumberFormat()
    private String contact;
    private String address;
    private LocalDateTime deliveryDate;
    private LocalDateTime deliveryAt;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
}
