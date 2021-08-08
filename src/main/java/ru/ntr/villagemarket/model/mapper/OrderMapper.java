package ru.ntr.villagemarket.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ntr.villagemarket.config.AppProperties;
import ru.ntr.villagemarket.model.dto.order.OrderDto;
import ru.ntr.villagemarket.model.dto.product.ProductDto;
import ru.ntr.villagemarket.model.dto.order.NewOrderDto;
import ru.ntr.villagemarket.model.dto.order.OrderBasicDto;
import ru.ntr.villagemarket.model.dto.order.OrderWithHistoryDto;
import ru.ntr.villagemarket.model.entity.Order;
import ru.ntr.villagemarket.model.entity.OrderedProduct;
import ru.ntr.villagemarket.model.helpers.ProductItem;
import ru.ntr.villagemarket.model.repository.OrderStatusRepository;
import ru.ntr.villagemarket.model.repository.ProductRepository;
import ru.ntr.villagemarket.model.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderStatusRepository orderStatusRepository;

    private final OrderProductsMapper orderProductsMapper;
    private final UserService userService;
    private final OrderHistoryMapper orderHistoryMapper;

    public OrderBasicDto toOrderBasicDto(Order order) {

        return OrderBasicDto.builder()
                .id(order.getId())
                .username(order.getUser().getUsername())
                .status(order.getStatus().getStatus())
                .orderDate(order.getOrderDate())
                .deliveryDate(order.getDeliveryDate())
                .totalPrice(order.getProducts().stream()
                        .map(OrderedProduct::getPrice)
                        .reduce(0.0, Double::sum))
                .build();
    }

    public OrderDto toOrderDto(Order order) {
        var orderDto = new OrderDto(toOrderBasicDto(order));
        orderDto.setFirstName(order.getCustomerFirstName());
        orderDto.setLastName(order.getCustomerLastName());
        orderDto.setAddress(order.getCustomerAddress());
        orderDto.setPhoneNumber(order.getCustomerPhoneNumber());
        orderDto.setEmail(order.getCustomerEmail());
        orderDto.setComments(order.getComments());
        orderDto.setProducts(orderProductsMapper.fromProductDtoList(
                order.getProducts().stream()
                        .map(orderedProduct -> ProductDto.builder()
                                .id(orderedProduct.getProduct().getId())
                                .name(orderedProduct.getProduct().getName())
                                .price(orderedProduct.getPrice())
                                .build())
                        .collect(Collectors.toList())));
        return orderDto;
    }

    public OrderWithHistoryDto toOrderWithHistoryDto(Order order) {

        return OrderWithHistoryDto.builder()
                .id(order.getId())
                .username(order.getUser().getUsername())
                .firstName(order.getCustomerFirstName())
                .lastName(order.getCustomerLastName())
                .address(order.getCustomerAddress())
                .email(order.getCustomerEmail())
                .phoneNumber(order.getCustomerPhoneNumber())
                .status(order.getStatus().getStatus())
                .orderDate(order.getOrderDate())
                .deliveryDate(order.getDeliveryDate())
                .comments(order.getComments())
                .products(
                        orderProductsMapper.fromProductDtoList(
                                order.getProducts().stream()
                                        .map(orderedProduct -> ProductDto.builder()
                                                .id(orderedProduct.getProduct().getId())
                                                .name(orderedProduct.getProduct().getName())
                                                .price(orderedProduct.getPrice())
                                                .build())
                                        .collect(Collectors.toList())
                        ))
                .totalPrice(order.getProducts().stream()
                        .map(OrderedProduct::getPrice)
                        .reduce(0.0, Double::sum))
                .history(orderHistoryMapper.fromOrderHistory(order.getHistory()))
                .build();
    }

    // Mapper for new order

    public NewOrderDto toNewOrderDto(Order order) {

        return NewOrderDto.builder()
                .firstName(order.getCustomerFirstName())
                .lastName(order.getCustomerLastName())
                .address(order.getCustomerAddress())
                .email(order.getCustomerEmail())
                .phoneNumber(order.getCustomerPhoneNumber())
                .deliveryDate(getDeliveryDate())
                .build();
    }

    public Order toOrder(NewOrderDto newOrderDto) {

        return Order.builder()
                .customerFirstName(newOrderDto.getFirstName())
                .customerLastName(newOrderDto.getLastName())
                .customerAddress(newOrderDto.getAddress())
                .customerEmail(newOrderDto.getEmail())
                .customerPhoneNumber(newOrderDto.getPhoneNumber())
                .orderDate(new Date())
                .deliveryDate(newOrderDto.getDeliveryDate())
                .comments(newOrderDto.getComments())
                .status(orderStatusRepository.findByStatus("Located"))
                .user(userService.getCurrentUser())
                .build();
    }

    public List<OrderBasicDto> toOrderBasicDtos(List<Order> orderList) {
        return orderList.stream()
                .map(this::toOrderBasicDto)
                .sorted(Comparator.comparing(OrderBasicDto::getId))
                .collect(Collectors.toList());
    }

    public List<OrderDto> toOrderDtos(List<Order> orderList) {
        return orderList.stream()
                .map(this::toOrderDto)
                .sorted(Comparator.comparing(OrderBasicDto::getId))
                .collect(Collectors.toList());
    }

    public List<OrderWithHistoryDto> toOrderWithHistoryDtos(List<Order> orderList) {
        return orderList.stream()
                .map(this::toOrderWithHistoryDto)
                .sorted(Comparator.comparing(OrderBasicDto::getId))
                .collect(Collectors.toList());
    }

    //Helpers

    private Date getDeliveryDate() {
        var currentDate = new Date();
        var calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, AppProperties.DAYS_FOR_DELIVERY);
        return calendar.getTime();
    }


}
