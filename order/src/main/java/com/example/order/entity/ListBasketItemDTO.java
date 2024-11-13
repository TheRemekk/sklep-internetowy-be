package com.example.order.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListBasketItemDTO {
    private List<BasketItemDTO> basketProducts;
    private double summaryPrice;

}
