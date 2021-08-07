package ru.ntr.villagemarket.model.service;

import ru.ntr.villagemarket.model.entity.Role;

public interface RoleService {
    Role getRoleByName(String roleName);
}
