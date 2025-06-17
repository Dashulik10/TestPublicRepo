package com.example.java_ifortex_test_task.service;

import com.example.java_ifortex_test_task.dto.SessionResponseDTO;
import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.Session;
import com.example.java_ifortex_test_task.mapper.SessionMapper;
import com.example.java_ifortex_test_task.repository.SessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;


    public SessionResponseDTO getFirstDesktopSession() {
        Session firstSession = sessionRepository.getFirstDesktopSession(DeviceType.DESKTOP);

        if (firstSession == null) {
            throw new EntityNotFoundException("No session found for device type: " + DeviceType.DESKTOP);
        }

        return sessionMapper.toDto(firstSession);
    }

    // Returns only Sessions from Active users that were ended before 2025
    public List<SessionResponseDTO> getSessionsFromActiveUsersEndedBefore2025() {
        List<Session> sessions = sessionRepository.getSessionsFromActiveUsersEndedBefore2025(LocalDateTime.of(2025, 1, 1, 0, 0));

        if (sessions.isEmpty()) {
            throw new EntityNotFoundException("No sessions from active users ended before 2025.");
        }

        return sessions.stream()
                .map(sessionMapper::toDto)
                .toList();
    }
}