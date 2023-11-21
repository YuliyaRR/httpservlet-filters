package org.example.dto;

import org.example.entity.Role;

import java.util.Objects;
import java.util.Set;

public class UserDto {

    private Long id;
    private String password;
    private String name;
    private Set<Role> roles;

    public UserDto(Long id, String password, String name, Set<Role> roles) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRole() {
        return roles;
    }

    public void setRole(Set<Role> role) {
        this.roles = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto user = (UserDto) o;
        return Objects.equals(id, user.id) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, name, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", role=" + roles +
                '}';
    }
}
