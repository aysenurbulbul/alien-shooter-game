package com.example.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

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