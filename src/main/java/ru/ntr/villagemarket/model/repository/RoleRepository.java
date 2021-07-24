package ru.ntr.villagemarket.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ntr.villagemarket.model.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String roleName);
}
