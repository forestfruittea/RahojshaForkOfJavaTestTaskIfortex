package com.example.java_ifortex_test_task.service;

import com.example.java_ifortex_test_task.dto.SessionResponseDTO;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.mapper.SessionMapper;
import com.example.java_ifortex_test_task.repository.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    public SessionResponseDTO getFirstDesktopSession() {
        return Optional.ofNullable(sessionRepository.getFirstDesktopSession())
                .map(sessionMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No user with sessions found"));
    }
    public List<SessionResponseDTO> getSessionsFromActiveUsersEndedBefore2025() {
        LocalDateTime endOf2024 = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
        return sessionRepository.getSessionsFromActiveUsersEndedBefore2025(endOf2024)
                .stream()
                .map(p -> SessionResponseDTO.builder()
                        .id(p.getId())
                        .deviceType(DeviceType.fromCode(p.getDeviceType()))
                        .endedAtUtc(p.getEndedAtUtc())
                        .startedAtUtc(p.getStartedAtUtc())
                        .userId(p.getUserId())
                        .userFullName(p.getFirstName() + " " + p.getLastName())
                        .build())
                .toList();
    }
}
