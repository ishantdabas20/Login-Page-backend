package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserRoleRequest;
import org.example.dto.Userdto;
import org.example.entity.Permission;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    // CREATE USER
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        User createdUser = userService.createUser(user);

        return ResponseEntity.ok(createdUser);
    }

    // GET ALL USERS
    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('USER_READ')")
    public List<Userdto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> {

                    Role role = user.getRoles()
                            .stream()
                            .findFirst()
                            .orElse(null);

                    String roleName = role != null ? role.getName() : "NO_ROLE";

                    Set<String> permissions = role != null
                            ? role.getPermissions()
                            .stream()
                            .map(Permission::getName)
                            .collect(Collectors.toSet())
                            : Set.of();

                    return new Userdto(
                            user.getId(),
                            user.getUsername(),
                            roleName,
                            permissions
                    );
                })
                .toList();
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }



    // GET LOGGED-IN USER
    @GetMapping("/getuser")
    public Userdto getCurrentUser(Authentication authentication) {

        User user = userService.getUserByUsername(authentication.getName());

        Role role = user.getRoles()
                .stream()
                .findFirst()
                .orElse(null);

        String roleName = role != null ? role.getName() : "NO_ROLE";

        Set<String> permissions = role != null
                ? role.getPermissions()
                .stream()
                .map(Permission::getName)
                .collect(Collectors.toSet())
                : Set.of();

        return new Userdto(
                user.getId(),
                user.getUsername(),
                roleName,
                permissions
        );
    }

    // UPDATE USER
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User user
    ) {
        return userService.updateUser(id, user);
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    // ASSIGN ROLE
    @PutMapping("/assignrole")
    public ResponseEntity<User> assignRole(@RequestBody UserRoleRequest userRoleRequest) throws RoleNotFoundException {

        User user = userService.assignRole(
                userRoleRequest.getUserId(),
                userRoleRequest.getRoleId()
        );

        return ResponseEntity.ok(user);

    }
}