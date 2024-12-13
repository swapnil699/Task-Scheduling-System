package org.example.taskscheduling.services;

import org.example.taskscheduling.dtos.userCretionDTO;
import org.example.taskscheduling.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public User createUser(userCretionDTO userDto);
    public void deleteUser(Long id);
    public User updateUser(Long id, userCretionDTO userDto);
    public User getUserById(Long id);
    public List<User> getAllUsers();
}
