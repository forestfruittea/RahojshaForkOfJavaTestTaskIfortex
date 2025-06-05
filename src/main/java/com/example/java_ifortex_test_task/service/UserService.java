package com.example.java_ifortex_test_task.service;

import com.example.java_ifortex_test_task.dto.UserResponseDTO;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.User;
import com.example.java_ifortex_test_task.mapper.UserMapper;
import com.example.java_ifortex_test_task.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO getUserWithMostSessions() {
        return Optional.ofNullable(userRepository.getUserWithMostSessions())
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No user with sessions found"));
    }

    public List<UserResponseDTO> getUsersWithAtLeastOneMobileSession() {
        List<User> users = userRepository.getUsersWithAtLeastOneMobileSession(DeviceType.MOBILE);
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

}
