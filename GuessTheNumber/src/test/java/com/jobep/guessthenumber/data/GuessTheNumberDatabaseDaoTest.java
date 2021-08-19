/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.guessthenumber.data;

import com.jobep.guessthenumber.TestApplicationConfiguration;
import com.jobep.guessthenumber.models.Game;
import com.jobep.guessthenumber.models.Round;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author powel
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GuessTheNumberDatabaseDaoTest {
    
    @Autowired
    GuessTheNumberDatabaseDao dao;
    
    public GuessTheNumberDatabaseDaoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Game> games = dao.getAllGames();
        List<Round> rounds = dao.getAllRounds();
        for(Game game : games){
            dao.deleteGameById(game.getId());
        }
        for(Round round : rounds){
            dao.deleteRoundById(round.getId());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    public void testSomeMethod() {
    }
    
}
