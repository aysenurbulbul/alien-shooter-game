package com.example.server.repository;

import com.example.server.model.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderboardRepository extends JpaRepository<Leaderboard,Long> {
}
