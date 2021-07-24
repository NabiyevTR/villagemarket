package ru.ntr.villagemarket.model.service;

import ru.ntr.villagemarket.model.dto.ProductDto;
import ru.ntr.villagemarket.model.entity.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String roleName);
}
