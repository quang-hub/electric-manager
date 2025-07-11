package com.example.rentmanager.repository;

import com.example.rentmanager.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository  extends JpaRepository<Room, Long> {
	Optional<Room> findByRoomName(String roomName);
}
