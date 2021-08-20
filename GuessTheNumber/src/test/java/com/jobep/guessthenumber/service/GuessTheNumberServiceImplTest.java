/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.guessthenumber.service;

import com.jobep.guessthenumber.models.Game;
import com.jobep.guessthenumber.models.Round;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author powel
 */
@SpringBootTest
public class GuessTheNumberServiceImplTest {
    
    @Autowired
    GuessTheNumberServiceImpl service;
    public GuessTheNumberServiceImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        service.clearDB();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of createNewGame method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testCreateNewGame() {
        int gameId = service.createNewGame();
        Game game = service.getGame(gameId);
        String answer = game.getAnswer();
        
        assertEquals(answer.length(),4,"Generated Answer not correct length");
        assertEquals(game.getStatus(),"In-Progress","");
    }

    /**
     * Test of processGuess method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testProcessGuess() {
        Game game = new Game();
        game.setStatus("In-Progress");
        game.setAnswer("4321");
        service.addGame(game);

        String rightGuess = "4321";
        String wrongGuess = "0123";
        
        Round round = service.processGuess(wrongGuess, game.getId());
        game = service.getGameById(game.getId());
        assertNotEquals(round.getResult(),"e:4p:0","Result not valid for wrong guess");
        assertEquals(game.getStatus(),"In-Progress", "Game status changed with wrong guess");
        
        
        round = service.processGuess(rightGuess, game.getId());
        game = service.getGameById(game.getId());
        assertEquals(round.getResult(),"e:4p:0", "Wrong result returned");
        assertEquals(game.getStatus(),"Finished","Game status not changed with correct guess");
        
        
    }

    /**
     * Test of getAllGames method, of class GuessTheNumberServiceImpl.
     */
    @Test
    public void testGetAllGames() {
        int gameId1 = service.createNewGame();
        int gameId2 = service.createNewGame();
        
        List<Game> games = service.getAllGames();
        Game game1 = service.getGameById(gameId1);
        Game game2 = service.getGameById(gameId2);
        
        
        assertEquals(games.size(),2,"Incorrect number of items in list");
        assertTrue(games.contains(game1),"Game 1 not in list");
        assertTrue(games.contains(game2),"Game 2 not in list");
    } 
    
    @Test
    public void testGetGameById(){
        int gameId1 = service.createNewGame();
        int gameId2 = service.createNewGame();
        Game game1 = service.getGame(gameId1);
        
        service.processGuess(game1.getAnswer(), gameId1);
        
        game1 = service.getGameById(gameId1);
        Game game2 = service.getGameById(gameId2);
        
        assertEquals(game2.getAnswer(), "", "In-progress game answer not invisible");
        assertNotEquals(game1.getAnswer(),"","Finished game's answer is invisible");
        
    }
}
