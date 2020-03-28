package com.example.server.service;

import com.example.server.model.Game;
import com.example.server.model.Leaderboard;
import com.example.server.repository.LeaderboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class LeaderboardService {

    private final LeaderboardRepository leaderboardRepository;

    private void updateEmptyLeaderboard(Game game){

        Leaderboard leaderboard = new Leaderboard();
        List<Game> temp = new ArrayList<>();
        temp.add(game);
        leaderboard.setAllTimeHighestScores(temp);
        leaderboard.setLastSevenDaysHighestScores(temp);
        leaderboard.setLastThirtyDaysHighestScores(temp);
        leaderboardRepository.save(leaderboard);
    }

    private void updateLeaderboard(List<Game> leaderboardList, Game game){

        int listSize = leaderboardList.size();
        int lastGameIndex = listSize -1;
        if(listSize<=20){
            leaderboardList.add(game);
        }
        else if(leaderboardList.get(lastGameIndex).getScore()< game.getScore()){
            leaderboardList.remove(lastGameIndex);
            leaderboardList.add(game);
        }
    }

    void updateAllLeaderboards(Game game){

        if(leaderboardRepository.findAll().isEmpty()){
            updateEmptyLeaderboard(game);
        }
        else{
            Leaderboard leaderboard = leaderboardRepository.findAll().get(0);
            updateLeaderboard(leaderboard.getAllTimeHighestScores(), game);
            updateLeaderboard(leaderboard.getLastSevenDaysHighestScores(), game);
            updateLeaderboard(leaderboard.getLastThirtyDaysHighestScores(), game);
        }
    }

    public List<Game> getAllTimes(){
        return leaderboardRepository.findAll().get(0).getAllTimeHighestScores();
    }

    public List<Game> getLastThirtyDays(){
        return leaderboardRepository.findAll().get(0).getLastThirtyDaysHighestScores();
    }

    public List<Game> getLastSevenDays(){
        return leaderboardRepository.findAll().get(0).getLastSevenDaysHighestScores();
    }

    public void clearAll(){ leaderboardRepository.deleteAll();}

    @Scheduled( cron = "0 1 * * *")
    public void deleteExpiredLeaderboardEntities(){
        Leaderboard leaderboard = leaderboardRepository.findAll().get(0);
        LocalDateTime now = LocalDateTime.now();
        Iterator<Game> gameIterator = leaderboard.getLastThirtyDaysHighestScores().iterator();
        while (gameIterator.hasNext()) {
            Game game = gameIterator.next();
            if(game.getFinishDateTime().isBefore(now.minusDays(30   ))){
                gameIterator.remove();
            }
        }
        gameIterator = leaderboard.getLastSevenDaysHighestScores().iterator();
        while (gameIterator.hasNext()) {
            Game game = gameIterator.next();
            if(game.getFinishDateTime().isBefore(now.minusDays(7))){
                gameIterator.remove();
            }
        }
    }

}



















