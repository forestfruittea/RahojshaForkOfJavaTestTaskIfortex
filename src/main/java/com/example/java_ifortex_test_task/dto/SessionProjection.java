package com.example.java_ifortex_test_task.dto;

import java.time.LocalDateTime;

public interface SessionProjection {
    Long getId();
    Integer getDeviceType(); // Enum as ordinal
    LocalDateTime getEndedAtUtc();
    LocalDateTime getStartedAtUtc();
    Long getUserId();
    String getFirstName();
    String getLastName();
}
