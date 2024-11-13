package com.example.order.translators;

import com.example.order.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract  class OrderToOrderDTO {
    public OrderDTO toOrderDTO(Order order){
        return translate(order);
    }


    @Mappings({
            @Mapping(target = "customerDetails",expression = "java(translateToCustomer(order))"),
            @Mapping(target = "address",expression = "java(translateAddres(order))"),
            @Mapping(target = "deliver",expression = "java(translateDeliver(order.getDeliver()))"),
    })
    protected abstract OrderDTO translate(Order order);

    @Mappings({})
    protected abstract CustomerDetails translateToCustomer(Order order);

    @Mappings({})
    protected abstract Address translateAddres(Order order);

    @Mappings({})
    protected abstract DeliverDTO translateDeliver(Deliver deliver);

}
