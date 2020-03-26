package com.example.server.repository;

import com.example.server.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player,Long> {
    Player findByUsername(String username);
}
