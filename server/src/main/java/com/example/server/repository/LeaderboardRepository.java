package com.example.server.repository;

import com.example.server.model.Game;
import com.example.server.model.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaderboardRepository extends JpaRepository<Leaderboard,Long> {


}
