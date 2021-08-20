/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.guessthenumber.data;

import com.jobep.guessthenumber.models.Game;
import com.jobep.guessthenumber.models.Round;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    
    @Test
    public void testGetAdd() {
        
        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("In Progress");
        
        dao.addGame(game);
        Game retGame = dao.findGameById(game.getId());
        
        assertEquals(game, retGame, "Game not properly retrieved");
        
        Round round = new Round();
        round.setGuess("1234");
        round.setGameId(game.getId());
        round.setResult("p:0e:0");
        
        dao.addRound(round);
        Round retRound = dao.findRoundById(round.getId());
        assertEquals(round,retRound, "Round not properly retrieved: Expected " + round.toString() + "\nRetrieved " + retRound.toString());
        
        
    }
    
    @Test
    public void testGetAll(){
        assertEquals(0,dao.getAllGames().size(), "Games DB not empty");
        assertEquals(0,dao.getAllRounds().size(), "Rounds DB not empty");
        
        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("In Progress");
        
        Game game2 = new Game();
        game2.setAnswer("4321");
        game2.setStatus("In Progress");
        
        dao.addGame(game);
        dao.addGame(game2);
        
        Round round = new Round();
        round.setGuess("1234");
        round.setGameId(game.getId());
        round.setResult("p:0e:0");
        
        Round round2 = new Round();
        round2.setGuess("4321");
        round2.setGameId(game2.getId());
        round2.setResult("p:2e:1");
        
        dao.addRound(round);
        dao.addRound(round2);
        
        List<Game> allGames = dao.getAllGames();
        List<Round> allRounds = dao.getAllRounds();
        
        assertEquals(2,allGames.size(),"Both games not properly added: expected 2, recieved " + allGames.size());
        assertTrue(allGames.contains(game), "First Game not in list");
        assertTrue(allGames.contains(game2), "Last Game not in list");
        
        assertEquals(2,allRounds.size(),"Both rounds not properly added: expected 2, recieved " + allRounds.size());
        assertTrue(allRounds.contains(round),"First Round not in list");
        assertTrue(allRounds.contains(round2), "Last Round not in list");
    }
    
    @Test
    public void testUpdate(){
        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("In Progress");
        
        dao.addGame(game);
        
        Round round = new Round();
        round.setGuess("1234");
        round.setGameId(game.getId());
        round.setResult("p:0e:0");
        
        round = dao.addRound(round);
        
        Game gameFromDao = dao.findGameById(game.getId());
        Round roundFromDao = dao.findRoundById(round.getId());
        
        assertEquals(game,gameFromDao,"Game retrieved does not match game inserted");
        assertEquals(round,roundFromDao,"Round retrieved does not match round inserted");
        
        game.setAnswer("4321");
        round.setGuess("4321");
        
        dao.updateGame(game);
        dao.updateRound(round);
        
        assertNotEquals(game,gameFromDao, "Game not changed in memory");
        assertNotEquals(round,roundFromDao, "Round not changed in memory");
        
        gameFromDao = dao.findGameById(game.getId());
        roundFromDao = dao.findRoundById(round.getId());
        
        assertEquals(game,gameFromDao,"Game not updated in DB");
        assertEquals(round,roundFromDao,"Round not updated in DB");
    }
    
    @Test
    public void testDeleteById(){
        Game game = new Game();
        game.setAnswer("1234");
        game.setStatus("In Progress");
        
        dao.addGame(game);
        
        Round round = new Round();
        round.setGuess("1234");
        round.setGameId(game.getId());
        round.setResult("p:0e:0");
        
        dao.addRound(round);
        
        dao.deleteRoundById(round.getId());
        
        assertFalse(dao.getAllRounds().contains(round),"Round not properly removed from DB (deleteRoundById)");
        
        dao.addRound(round);
        
        dao.deleteGameById(game.getId());
        
        assertFalse(dao.getAllRounds().contains(round),"Round not properly removed from DB (deleteGameById");
        assertFalse(dao.getAllGames().contains(game),"Game not properly removed from DB");
    }
    
    
}
