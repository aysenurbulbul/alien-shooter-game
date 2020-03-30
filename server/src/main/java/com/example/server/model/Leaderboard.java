package com.example.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

//In this table there will be only one entry.
//which will kept the leaderboard lists separately.
//It will be update everyday on 00.00am and
//after every match if necessary.
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LEADERBOARD")
public class Leaderboard {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "leaderboard_idone")
    @OrderBy("score DESC")
    private List<Game> lastSevenDaysHighestScores;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "leaderboard_idtwo")
    @OrderBy("score DESC")
    private List<Game> lastThirtyDaysHighestScores;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "leaderboard_id")
    @OrderBy("score DESC")
    private List<Game> allTimeHighestScores;

}