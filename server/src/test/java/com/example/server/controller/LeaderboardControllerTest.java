package com.example.server.controller;

import com.example.server.service.LeaderboardService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.Silent.class)
@AutoConfigureMockMvc
public class LeaderboardControllerTest {

    @Mock
    LeaderboardService leaderboardService;

    @InjectMocks
    LeaderboardController leaderboardController;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(leaderboardController).build();
    }

    @Test
    public void getLastSevenDaysTest() throws Exception {

        mockMvc.perform(get("/leaderboard/getLastSevenDays")).andExpect(status().isOk());
        then(leaderboardService).should().getLastSevenDays();


    }
    @Test
    public void getLastThirtyDaysTest()throws Exception {
        mockMvc.perform(get("/leaderboard/getLastThirtyDays")).andExpect(status().isOk());
        then(leaderboardService).should().getLastThirtyDays();

    }
    @Test
    public void getAllTimesTest()throws Exception {

        mockMvc.perform(get("/leaderboard/getAllTimes")).andExpect(status().isOk());
        then(leaderboardService).should().getAllTimes();
    }
    @Test
    public void deleteAllTest()throws Exception {

        mockMvc.perform(delete("/leaderboard/delete")).andExpect(status().isOk());
        then(leaderboardService).should().clearAll();

    }

}