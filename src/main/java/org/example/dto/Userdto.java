package org.example.dto;

import java.util.Set;

public class Userdto {

    private Long id;
    private String username;
    private String role;
    private Set<String> permissions;

    public Userdto(Long id, String username, String role, Set<String> permissions) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.permissions = permissions;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public Set<String> getPermissions() {
        return permissions;
    }
}