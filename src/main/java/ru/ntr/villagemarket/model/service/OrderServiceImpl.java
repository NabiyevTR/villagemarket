package ru.ntr.villagemarket.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ntr.villagemarket.config.AppProperties;
import ru.ntr.villagemarket.model.dto.order.NewOrderDto;
import ru.ntr.villagemarket.model.dto.order.OrderDtoBasic;
import ru.ntr.villagemarket.model.dto.order.OrderWithHistoryDto;
import ru.ntr.villagemarket.model.entity.*;
import ru.ntr.villagemarket.model.mapper.OrderMapper;
import ru.ntr.villagemarket.model.repository.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderedProductRepository orderedProductRepository;

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


        // Не получилось напрямую получить продукты из корзины и положить в заказ
        // org.hibernate.HibernateException: Found shared references to a collection
        // Поэтому пришлось сделать через productIds. Так можно?
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
    public List<OrderDtoBasic> findAll() {
        return orderMapper.fromOrders(orderRepository.findAll());
    }

    @Override
    public List<OrderDtoBasic> findAllActive() {
        return orderMapper.fromOrders(orderRepository.findAllByStatusIsNotIn(new String[]{"Closed", "Canceled"}));
    }

    @Override
    public OrderWithHistoryDto findById(int id) {
        //TODO handle optional

        return orderMapper.fromOrderToOrderWithHistoryDto(orderRepository.findById(id).get());
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
        calendar.add(Calendar.DATE, AppProperties.DAYS_FOR_DELIVERY);
        return calendar.getTime();
    }

}
