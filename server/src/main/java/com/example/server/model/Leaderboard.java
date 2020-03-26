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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "leaderboard_id")
    private List<Game> lastSevenDaysHighestScores;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "leaderboard_id")
    private List<Game> lastThirtyDaysHighestScores;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "leaderboard_id")
    private List<Game> allTimeHighestScores;

}