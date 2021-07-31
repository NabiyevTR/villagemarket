package ru.ntr.villagemarket.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ntr.villagemarket.model.entity.Role;
import ru.ntr.villagemarket.model.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String roleName) {
        //TODO DELETE FOR TEST
        Role role = roleRepository.findByRole(roleName);
        System.out.println("Role name: " + roleName);
        System.out.println("Role: "+ role);

        return roleRepository.findByRole(roleName);
    }
}
