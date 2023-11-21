package org.example.service;

import org.example.dto.RoleDto;

public interface IRoleService {
    RoleDto createRole(RoleDto role);
    RoleDto updateRole(RoleDto role);
    boolean deleteRole(Long id);
    RoleDto getRole(Long id);
}
