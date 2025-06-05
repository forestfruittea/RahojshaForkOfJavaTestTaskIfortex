package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.dto.SessionProjection;
import com.example.java_ifortex_test_task.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query(value = """
        SELECT s.id, s.device_type, s.ended_at_utc, s.started_at_utc, s.user_id
        FROM sessions s
        JOIN users u ON s.user_id = u.id
        WHERE s.device_type = 1 AND u.deleted = false
        ORDER BY s.started_at_utc ASC
        LIMIT 1
        """, nativeQuery = true)
    Session getFirstDesktopSession();

    @Query(value = """
        SELECT s.id, s.device_type, s.ended_at_utc, s.started_at_utc,
               u.id AS userId, u.first_name, u.last_name
        FROM sessions s
        JOIN users u ON s.user_id = u.id
        WHERE s.ended_at_utc < :dateTime AND u.deleted = false
        """, nativeQuery = true)
    List<SessionProjection> getSessionsFromActiveUsersEndedBefore2025(@Param("dateTime") LocalDateTime dateTime);

}