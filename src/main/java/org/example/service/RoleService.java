package org.example.service;

import org.example.dto.RoleDto;
import org.example.entity.Role;
import org.example.repository.RoleRepository;
public class RoleService implements IRoleService{

    private final RoleRepository roleRepository;

    public RoleService() {
        this.roleRepository = new RoleRepository();
    }

    public RoleDto createRole(RoleDto role) {
        Role roleDB = roleRepository.create(new Role(role.getRole()));
        return new RoleDto(roleDB.getId(), roleDB.getRole());
    }

    public RoleDto updateRole(RoleDto role) {
        Role role1 = roleRepository.getById(role.getId()).orElseThrow(() -> new RuntimeException("Role not found"));
        role1.setRole(role.getRole());
        Role roleDB = roleRepository.update(role1);
        return new RoleDto(roleDB.getId(), roleDB.getRole());
    }

    public boolean deleteRole(Long id) {
        return roleRepository.deleteById(id);
    }

    public RoleDto getRole(Long id) {
        Role roleDB = roleRepository.getById(id).orElseThrow(() -> new RuntimeException("Role not found"));

        return new RoleDto(roleDB.getId(), roleDB.getRole());
    }
}
