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


    //this function is only called if leaderboard is empty
    //first creates leaderboard,
    //then adds the given game to all lists
    void updateEmptyLeaderboard(Game game){

        Leaderboard leaderboard = new Leaderboard();
        List<Game> temp = new ArrayList<>();
        temp.add(game);
        leaderboard.setAllTimeHighestScores(temp);
        leaderboard.setLastSevenDaysHighestScores(temp);
        leaderboard.setLastThirtyDaysHighestScores(temp);
        leaderboardRepository.save(leaderboard);
    }

    //updates the given leaderboard list.
    //If the leaderboard is not bigger then 20 directly adds it.
    //If the leaderboard list is not bigger than 20,
        //First checks, the smallest score then if it biggerst then that,
        //removes the smallest score and adds the current one.
    //Since all the Leaderboard list kept in the database ordered by their
    //scores see(package com.example.server.model.Leaderboard -> @OrderBy("score DESC"))
    // inserting to right place is not an issue.
    void updateLeaderboard(List<Game> leaderboardList, Game game){

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

        //if the leaderboard is empty aka the first game is played.
        if(leaderboardRepository.findAll().isEmpty()){
            updateEmptyLeaderboard(game);
        }
        //if the leaderboard is not empty checks all of the 3 leaderboard lists if it can make it.
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


    //This function is called everyday on 00.00 am
    //It deletes the expired entities from Leaderboard's lastSevenDaysHighestScores and
    //lastThirtyDaysHighestScores.
    @Scheduled( cron = "0 0 * * * *")
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



















