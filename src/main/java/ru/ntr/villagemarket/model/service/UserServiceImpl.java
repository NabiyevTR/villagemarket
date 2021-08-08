package ru.ntr.villagemarket.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ntr.villagemarket.exceptions.*;
import ru.ntr.villagemarket.model.dto.user.*;
import ru.ntr.villagemarket.model.entity.User;
import ru.ntr.villagemarket.model.mapper.CustomerMapper;
import ru.ntr.villagemarket.model.mapper.UserMapper;
import ru.ntr.villagemarket.model.mapper.UserProfileMapper;
import ru.ntr.villagemarket.model.repository.OrderRepository;
import ru.ntr.villagemarket.model.repository.RoleRepository;
import ru.ntr.villagemarket.model.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrderRepository orderRepository;

    private final CustomerMapper customerMapper;
    private final UserMapper userMapper;
    private final UserProfileMapper userProfileMapper;

    @Override
    public List<UserDto> findAll() {
        return userMapper.fromUsers(userRepository.findAll());
    }

    @Override
    public List<CustomerBasicDto> findAllCustomers() {
        return customerMapper.toCustomerBasicDto(
                userRepository.findUsersByRolesContains(roleRepository.findByRole("CUSTOMER")
                ));
    }

    @Override
    public UserDto findById(int id) {
        Optional<User> user = userRepository.findUserById(id);
        if (user.isEmpty()) {
            throw new NoSuchUserException(id);
        } else {
            return userMapper.fromUser(user.get());
        }
    }

    @Override
    public CustomerFullDto findCustomerById(int id) {
        Optional<User> user = userRepository.findUserById(id);
        if (user.isEmpty()) {
            throw new NoSuchUserException(id);
        } else {
            return customerMapper.toCustomerFullDto(user.get());
        }
    }

    @Override
    public void save(UserDto userDto) {
        userRepository.save(userMapper.toUser(userDto));
    }


    @Override
    public void regUser(NewUserDto newUserDto) {

        // Check if user with such username exists
        Optional<User> optionalUser = userRepository.findUserByUsername(newUserDto.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserWithSuchUsernameExistsException();
        }

        // Check if user with such email exists
        optionalUser = userRepository.findUserByEmail(newUserDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserWithSuchEmailExistsException();
        }

        // Check if user with such phone number exists
        optionalUser = userRepository.findUserByPhoneNumber(newUserDto.getPhoneNumber());
        if (optionalUser.isPresent()) {
            throw new UserWithSuchPhoneNumberExistsException();
        }

        userRepository.save(userMapper.toUser(newUserDto));
    }



    @Transactional
    @Override
    public void delete(int id) {

        Optional<User> userToDelete = userRepository.findUserById(id);

        if (userToDelete.isPresent()) {
            orderRepository.deleteOrdersByUser(userToDelete.get());
            userRepository.deleteById(id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(optionalUser.get());
    }

    @Override
    public UserProfileDto getCurrentUserProfile() {
        return userProfileMapper.fromUser(getCurrentUser());
    }

    @Override
    public User getCurrentUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = principal instanceof UserDetails
                ? ((UserDetails) principal).getUsername()
                : principal.toString();

        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new NoCurrentUserException();
        }
        return optionalUser.get();
    }

}
