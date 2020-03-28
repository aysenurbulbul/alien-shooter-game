package com.example.server.service;

import com.example.server.model.Player;
import com.example.server.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Player addPlayer(final Player player){
        player.setPassword(bCryptPasswordEncoder.encode(player.getPassword()));
        return playerRepository.save(player);
    }

    public Player getPlayer(String username){
        return playerRepository.findByUsername(username);
    }

    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    public void deleteAllPlayers(){
        playerRepository.deleteAll();
    }

    public void deletePlayer(final Long playerId){
        playerRepository.deleteById(playerId);
    }
}
