package ru.ntr.villagemarket.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ntr.villagemarket.model.dto.UserDto;
import ru.ntr.villagemarket.model.entity.User;
import ru.ntr.villagemarket.model.mapper.UserMapper;
import ru.ntr.villagemarket.model.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAll() {
        return userMapper.fromUsers(userRepository.findAll());
    }

    @Override
    public UserDto findById(int id) {
        return userMapper.fromUser(userRepository.findUserById(id));
    }

    @Override
    public UserDto findCurrent() {
        return userMapper.fromUser(userRepository.findUserById(getCurrentUserId()));
    }

    @Override
    public void save(UserDto userDto) {
        userRepository.save(userMapper.toUser(userDto));
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }

    private int getCurrentUserId() {
        return ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUser()
                .getId();
    }

}
