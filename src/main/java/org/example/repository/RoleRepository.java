package org.example.repository;

import org.example.entity.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RoleRepository implements CRUDRepository<Role>{
    private static Long id = 0L;

    private static Map<Long, Role> roles = new HashMap<>();

    static {
        roles.put(++id, new Role(id, "USER"));
        roles.put(++id, new Role(id, "ADMIN"));
    }

    @Override
    public Role create(Role role) {
        role.setId(++id);
        roles.put(id, role);
        return roles.get(id);
    }

    @Override
    public Role update(Role role) {
        roles.put(role.getId(), role);
        return role;
    }

    @Override
    public Optional<Role> getById(Long id) {
        return Optional.ofNullable(roles.get(id));
    }

    @Override
    public boolean deleteById(Long id) {
        return roles.remove(id) != null;
    }
}
