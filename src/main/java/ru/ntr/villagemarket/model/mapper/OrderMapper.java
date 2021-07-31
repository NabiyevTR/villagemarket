package ru.ntr.villagemarket.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ntr.villagemarket.config.AppProperties;
import ru.ntr.villagemarket.model.dto.ProductDto;
import ru.ntr.villagemarket.model.dto.order.NewOrderDto;
import ru.ntr.villagemarket.model.dto.order.OrderDtoBasic;
import ru.ntr.villagemarket.model.dto.order.OrderWithHistoryDto;
import ru.ntr.villagemarket.model.entity.Order;
import ru.ntr.villagemarket.model.entity.OrderedProduct;
import ru.ntr.villagemarket.model.entity.Product;
import ru.ntr.villagemarket.model.repository.OrderStatusRepository;
import ru.ntr.villagemarket.model.repository.ProductRepository;
import ru.ntr.villagemarket.model.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderStatusRepository orderStatusRepository;
    private final ProductRepository productRepository;
    private final OrderProductsMapper orderProductsMapper;
    private final UserService userService;
    private final OrderHistoryMapper orderHistoryMapper;


     /*
    public Order toOrder(OrderDtoBasicInfo orderDtoBasicInfo) {

      return Order.builder()
                .id(orderDtoBasicInfo.getId())
                .status(orderStatusRepository.getById(orderDtoBasicInfo.getStatus()))
                .products(orderProductsMapper.toProductList(orderDtoBasicInfo.getProducts()))
                .customerFirstName(orderDtoBasicInfo.getFirstName())
                .customerLastName(orderDtoBasicInfo.getLastName())
                .customerAddress(orderDtoBasicInfo.getAddress())
                .customerEmail(orderDtoBasicInfo.getEmail())
                .customerPhoneNumber(orderDtoBasicInfo.getPhoneNumber())
                .build();
    }*/

    public OrderDtoBasic fromOrderToOrderBasicDto(Order order) {

        return OrderDtoBasic.builder()
                .id(order.getId())
                .username(order.getUser().getUsername())
                .status(order.getStatus().getStatus())
                .orderDate(order.getOrderDate())
                .deliveryDate(order.getDeliveryDate())
                .totalPrice(order.getProducts().stream()
                        .map(e -> e.getPrice())
                        .reduce(0.0, Double::sum))
                .build();
    }

    public OrderWithHistoryDto fromOrderToOrderWithHistoryDto(Order order) {

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
                                        .map(orderedProduct -> {
                                            return ProductDto.builder()
                                                    .id(orderedProduct.getId())
                                                    .name(orderedProduct.getProduct().getName())
                                                    .price(orderedProduct.getProduct().getPrice())
                                                    .build();
                                        })
                                        .collect(Collectors.toList())
                        ))
                .totalPrice(order.getProducts().stream()
                        .map(OrderedProduct::getPrice)
                        .reduce(0.0, Double::sum))
                .history(orderHistoryMapper.fromOrderHistory(order.getHistory()))
                .build();
    }

    // Mapper for new order

    public NewOrderDto fromOrderToNewOrderDto(Order order) {

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


    //Helpers

    private Date getDeliveryDate() {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, AppProperties.DAYS_FOR_DELIVERY);
        return calendar.getTime();
    }

  /*  public List<Order> toProducts(List<OrderDtoBasicInfo> orderDtoBasicInfoList) {
        return orderDtoBasicInfoList.stream()
                .map(this::toOrder)
                .sorted(Comparator.comparing(Order::getId))
                .collect(Collectors.toList());
    }*/

    public List<OrderDtoBasic> fromOrders(List<Order> orderList) {
        return orderList.stream()
                .map(this::fromOrderToOrderBasicDto)
                .sorted(Comparator.comparing(OrderDtoBasic::getId))
                .collect(Collectors.toList());
    }
}
