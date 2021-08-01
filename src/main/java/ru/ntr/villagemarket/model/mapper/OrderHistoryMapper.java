package ru.ntr.villagemarket.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ntr.villagemarket.model.dto.order.OrderHistoryItemDto;
import ru.ntr.villagemarket.model.entity.OrderHistoryItem;
import ru.ntr.villagemarket.model.repository.OrderRepository;
import ru.ntr.villagemarket.model.repository.OrderStatusRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderHistoryMapper {
    private final OrderStatusRepository orderStatusRepository;
    private final OrderRepository orderRepository;

    public OrderHistoryItem toOrderHistoryItem(OrderHistoryItemDto orderHistoryItemDto) {

        return OrderHistoryItem.builder()
                .id(orderHistoryItemDto.getId())
                .order(orderRepository.getById(orderHistoryItemDto.getOrderId()))
                .orderStatus(orderStatusRepository.findByStatus(orderHistoryItemDto.getStatus()))
                .date(orderHistoryItemDto.getDate())
                .build();
    }

    public OrderHistoryItemDto fromOrderHistoryItem(OrderHistoryItem orderHistoryItem) {

        return OrderHistoryItemDto.builder()
                .id(orderHistoryItem.getId())
                .orderId(orderHistoryItem.getOrder().getId())
                .status(orderHistoryItem.getOrderStatus().getStatus())
                .date(orderHistoryItem.getDate())
                .build();
    }

    public List<OrderHistoryItem> toOrderHistory(List<OrderHistoryItemDto> orderHistoryItemDtoList) {
        return orderHistoryItemDtoList.stream()
                .map(this::toOrderHistoryItem)
                .sorted(Comparator.comparing(OrderHistoryItem::getId))
                .collect(Collectors.toList());
    }

    public List<OrderHistoryItemDto> fromOrderHistory(List<OrderHistoryItem> orderList) {
        return orderList.stream()
                .map(this::fromOrderHistoryItem)
                .sorted(Comparator.comparing(OrderHistoryItemDto::getId))
                .collect(Collectors.toList());
    }
}
