package ru.ntr.villagemarket.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.ntr.villagemarket.model.dto.OrderDto;
import ru.ntr.villagemarket.model.entity.User;
import ru.ntr.villagemarket.model.mapper.OrderMapper;
import ru.ntr.villagemarket.model.repository.OrderRepository;
import ru.ntr.villagemarket.model.repository.UserRepository;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;

    @Override
    public OrderDto dataForNewOrder() {

        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final String username = principal instanceof UserDetails
                ? ((UserDetails) principal).getUsername()
                : principal.toString();

        final User user = userRepository.findUserByUsername(username);

        return OrderDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .build();
    }

    @Override
    public void createOrder(OrderDto orderDto) {
        orderRepository.save(orderMapper.toOrder(orderDto));
    }


}
