package ru.ntr.villagemarket.model.dto.order;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderStatusChangeResponseDto {
    private String status;
    private List<OrderHistoryItemDto> history;
}
