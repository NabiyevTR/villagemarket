package ru.ntr.villagemarket.model.dto.order;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.PatchMapping;

@Data
@Builder
public class OrderStatusChangeRequestDto {

    private int statusId;
    private String status;

}
