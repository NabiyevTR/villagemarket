package ru.ntr.villagemarket.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ntr.villagemarket.config.AppProperties;
import ru.ntr.villagemarket.exceptions.NoSuchOrderException;
import ru.ntr.villagemarket.model.dto.order.*;
import ru.ntr.villagemarket.model.entity.*;
import ru.ntr.villagemarket.model.mapper.OrderHistoryMapper;
import ru.ntr.villagemarket.model.mapper.OrderMapper;
import ru.ntr.villagemarket.model.repository.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    //Properties
    private final AppProperties appProperties;

    // Mappers
    private final OrderMapper orderMapper;
    private final OrderHistoryMapper orderHistoryMapper;

    //Services

    private final CartService cartService;
    private final UserService userService;

    //Repositories
    private final OrderRepository orderRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final ProductRepository productRepository;

    @Override
    public NewOrderDto dataForNewOrder() {

        var currentUser = userService.getCurrentUser();

        return NewOrderDto.builder()
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .address(currentUser.getAddress())
                .email(currentUser.getEmail())
                .phoneNumber(currentUser.getPhoneNumber())
                .deliveryDate(getDeliveryDate())
                .build();
    }

    @Override
    public void createOrder(NewOrderDto newOrderDto) {

        //Creating new order
        var order = orderMapper.toOrder(newOrderDto);

        List<Integer> productIds = cartService.getCart().getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        List<OrderedProduct> products = productIds.stream()
                .map(id -> OrderedProduct.builder()
                        .product(productRepository.getById(id))
                        .price(productRepository.getById(id).getPrice())
                        .build())
                .collect(Collectors.toList());


        //Setting products to order
        order.setProducts(products);

        //Saving order to repository
        orderRepository.save(order);

        //Saving to history
        orderHistoryRepository.save(
                OrderHistoryItem.builder()
                        .order(order)
                        .orderStatus(orderStatusRepository.findByStatus("Located"))
                        .order(order)
                        .date(new Date())
                        .build()
        );

        //Delete all from cart
        cartService.clearCart();
    }

    @Override
    public List<OrderBasicDto> findAll() {
        return orderMapper.toOrderBasicDtos(orderRepository.findAll());
    }

    @Override
    public List<OrderBasicDto> findAllActive() {
        return orderMapper.toOrderBasicDtos(orderRepository.findAllByStatusNot(
                orderStatusRepository.findByStatus("Closed")
        ));
    }

    @Override
    public OrderWithHistoryDto findById(int id) {
              return orderMapper.toOrderWithHistoryDto(orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchOrderException(id)));
    }

    @Override
    public OrderStatusChangeResponseDto changeStatus(int id, OrderStatusChangeRequestDto orderStatusChangeRequestDto) {

        var orderStatus = orderStatusRepository.findByStatus(orderStatusChangeRequestDto.getStatus());

        var order = orderRepository.findById(id).orElseThrow(() -> new NoSuchOrderException(id));

        order.setStatus(orderStatus);

        var orderHistoryItem = OrderHistoryItem.builder()
                .order(order)
                .date(new Date())
                .orderStatus(orderStatus)
                .build();

        orderHistoryRepository.save(orderHistoryItem);

        orderRepository.save(order);

        return OrderStatusChangeResponseDto.builder()
                .history(orderHistoryMapper.fromOrderHistory(order.getHistory()))
                .status(order.getStatus().getStatus())
                .build();
    }

    @Override
    public void save(NewOrderDto newOrderDto) {
        orderRepository.save(orderMapper.toOrder(newOrderDto));
    }

    @Override
    public void delete(int id) {
        orderRepository.deleteById(id);
    }

    //Helpers
    private Date getDeliveryDate() {
        var currentDate = new Date();
        var calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, appProperties.getDaysForDelivery());
        return calendar.getTime();
    }

}
