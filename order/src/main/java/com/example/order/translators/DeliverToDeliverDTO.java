package com.example.order.translators;

import com.example.order.entity.Deliver;
import com.example.order.entity.DeliverDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper
public abstract class DeliverToDeliverDTO {

    public DeliverDTO deliverDTO(Deliver deliver){
        return translate(deliver);
    }


    @Mappings({})
    protected abstract DeliverDTO translate(Deliver deliver);
}
