package com.example.geeks.repository;

import com.example.geeks.domain.MeetingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingHistoryRepository extends JpaRepository<MeetingHistory, Long> {
}
