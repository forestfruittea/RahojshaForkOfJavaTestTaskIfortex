package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
    SELECT u.*
    FROM users u
    JOIN sessions s ON u.id = s.user_id
    WHERE s.device_type = 1
    GROUP BY u.id, u.first_name, u.last_name, u.middle_name, u.email, u.deleted
    ORDER BY MAX(s.started_at_utc) DESC
""", nativeQuery = true)
    List<User> getUsersWithAtLeastOneMobileSession(DeviceType deviceType);

    @Query(value = """
    SELECT u.*
    FROM users u
    JOIN sessions s ON u.id = s.user_id
    GROUP BY u.id, u.first_name, u.last_name, u.middle_name, u.email, u.deleted
    ORDER BY COUNT(s.id) DESC
    LIMIT 1
""", nativeQuery = true)
    User getUserWithMostSessions();
}
