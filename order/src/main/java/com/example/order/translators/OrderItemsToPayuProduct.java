package com.example.order.translators;

import com.example.order.entity.OrderItems;
import com.example.order.entity.PayuProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class OrderItemsToPayuProduct {
    public PayuProduct toPayuProduct(OrderItems orderItems){
        return translate(orderItems);
    }


    @Mappings({
            @Mapping(source = "priceUnit",target = "unitPrice")
    })
    protected abstract PayuProduct translate(OrderItems orderItems);
}
