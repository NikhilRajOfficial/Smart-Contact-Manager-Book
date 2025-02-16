package com.SmartContactManager.services;

import java.util.List;
import java.util.Optional;

import com.SmartContactManager.entities.User;

public interface UserService {

    User saveUser(User user);
    Optional<User> getUserById(String id);
    Optional<User> updatUser(User user);
    void deleteUser(String id);
    boolean isUserExits(String userId);
    boolean isUserExitsByEmail(String email);

    List<User> getAllUsers();

     User getUserByEmail(String email);

}
