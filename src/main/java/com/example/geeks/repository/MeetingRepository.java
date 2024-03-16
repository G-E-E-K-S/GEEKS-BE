package com.example.geeks.repository;

import com.example.geeks.domain.ChatRoom;
import com.example.geeks.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
