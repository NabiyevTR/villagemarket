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
        var user = userRepository.findUserById(id).orElseThrow(() -> new NoSuchUserException(id));
        return userMapper.fromUser(user);
    }

    @Override
    public CustomerFullDto findCustomerById(int id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new NoSuchUserException(id));
        return customerMapper.toCustomerFullDto(user);
    }

    @Override
    public void save(UserDto userDto) {
        userRepository.save(userMapper.toUser(userDto));
    }

    @Override
    public void updateUserProfile(UpdateUserProfileDto updateUserProfileDto) {

        var currentUser = getCurrentUser();

        if (!currentUser.getPhoneNumber().equals(updateUserProfileDto.getPhoneNumber())) {
            checkIfPhoneNumberExists(updateUserProfileDto.getPhoneNumber());
        }

        if (!currentUser.getEmail().equals(updateUserProfileDto.getEmail())) {
            checkIfEmailExists(updateUserProfileDto.getEmail());
        }

        var updatedCurrentUser = (userMapper.toUser(updateUserProfileDto));

        updatedCurrentUser.setId(currentUser.getId());
        updatedCurrentUser.setUsername(currentUser.getUsername());
        updatedCurrentUser.setPassword(currentUser.getPassword());
        updatedCurrentUser.setRegDate(currentUser.getRegDate());
        updatedCurrentUser.setId(currentUser.getId());
        updatedCurrentUser.setBlocked(currentUser.isBlocked());
        updatedCurrentUser.setComments(currentUser.getComments());
        updatedCurrentUser.setRoles(currentUser.getRoles());

        userRepository.save(updatedCurrentUser);
    }


    @Override
    public void regUser(NewUserDto newUserDto) {

        checkIfUserNameExists(newUserDto.getUsername());
        checkIfEmailExists(newUserDto.getEmail());
        checkIfPhoneNumberExists(newUserDto.getPhoneNumber());

        userRepository.save(userMapper.toUser(newUserDto));
    }

    @Transactional
    @Override
    public void delete(int id) {

        var userToDelete = userRepository.findUserById(id)
                .orElseThrow(() -> new NoSuchUserException(id));

        if (userToDelete.equals(getCurrentUser())) {
            throw new CurrentUserDeleteException();
        }

        if (userToDelete.getRoles().stream()
                .anyMatch(role -> role.getRole().equals("SUPERADMIN"))
        ) {
            throw new SuperAdminDeleteException();
        }

        orderRepository.deleteOrdersByUser(userToDelete);
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserPrincipal(user);
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

        return userRepository.findUserByUsername(username)
                .orElseThrow(NoCurrentUserException::new);
    }

    private void checkIfUserNameExists(String username) {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            throw new UserWithSuchUsernameExistsException();
        }
    }

    private void checkIfEmailExists(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()) {
            throw new UserWithSuchEmailExistsException();
        }
    }

    private void checkIfPhoneNumberExists(String phoneNumber) {
        Optional<User> optionalUser = userRepository.findUserByPhoneNumber(phoneNumber);
        if (optionalUser.isPresent()) {
            throw new UserWithSuchPhoneNumberExistsException();
        }
    }

}
