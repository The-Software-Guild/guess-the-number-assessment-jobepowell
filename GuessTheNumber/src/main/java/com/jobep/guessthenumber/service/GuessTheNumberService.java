/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.guessthenumber.service;

import com.jobep.guessthenumber.models.Game;
import com.jobep.guessthenumber.models.Round;
import java.util.List;

/**
 *
 * @author powel
 */
public interface GuessTheNumberService {
    public int createNewGame();
    public Round processGuess(String guess, int gameId);
    public List<Game> getAllGames();
    public int addGame(Game game);
    public List<Round> getRoundsForGame(int gameId);
    public boolean clearDB();
    public Game getGameById(int gameId);
    
}
