/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.guessthenumber.data;

import com.jobep.guessthenumber.models.Game;
import com.jobep.guessthenumber.models.Round;
import java.util.List;

/**
 *
 * @author powel
 */
public interface GuessTheNumberDao {
    
    Round addRound(Round round);
    Game addGame(Game game);
    
    List<Round> getAllRoundsByTime();
    List<Round> getAllRounds();
    List<Game> getAllGames();

    Round findRoundById(int id);
    Game findGameById(int id);

    // true if item exists and is updated
    boolean updateRound(Round round);
    boolean updateGame(Game game);

    // true if item exists and is deleted
    boolean deleteRoundById(int id);
    boolean deleteGameById(int id);
    
    
}
