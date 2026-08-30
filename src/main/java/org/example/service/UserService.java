package org.example.service;

import org.example.entity.Role;
import org.example.entity.User;
import org.example.exception.UserNotFoundException;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // CREATE USER
    public User createUser(User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DataIntegrityViolationException("Username already exists: " + user.getUsername());
        }

        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
            );

        return userRepository.save(user);
    }


    // GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    // GET USER BY ID
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );
    }


    // GET USER BY USERNAME
    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found: " + username
                        )
                );
    }


    // UPDATE USER
    public User updateUser(Long id, User user) {

        User existingUser = getUserById(id);

        existingUser.setUsername(user.getUsername());


        if (user.getPassword() != null &&
                !user.getPassword().isEmpty()) {

            existingUser.setPassword(
                    passwordEncoder.encode(
                            user.getPassword()
                    )
            );
        }


        if (user.getRoles() != null && !user.getRoles().isEmpty()) {

            Set<Role> roles = user.getRoles().stream().map(role ->
                    {
                        try {
                            return roleRepository
                                    .findById(role.getId())
                                    .orElseThrow(() -> new RoleNotFoundException("Role not found"));
                        } catch (RoleNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toSet());

            existingUser.setRoles(roles);
        }


        return userRepository.save(existingUser);
    }


    // DELETE USER
    public String deleteUser(Long id) {

        User user = getUserById(id);

        userRepository.delete(user);

        return "User deleted";
    }

    public User assignRole(long userId, long roleId) throws RoleNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        user.getRoles().add(role);

        return userRepository.save(user);

    }
}