package ru.ntr.villagemarket.model.dto.order;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OrderHistoryItemDto {

    private int id;
    private int orderId;
    private String status;
    private Date date;
}
