package ru.ntr.villagemarket.model.dto.order;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import ru.ntr.villagemarket.model.entity.OrderHistoryItem;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class OrderWithHistoryDto extends OrderDto {

    private List<OrderHistoryItemDto> history;


}
