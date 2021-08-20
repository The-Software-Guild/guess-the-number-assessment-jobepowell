/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.guessthenumber.controllers;

import com.jobep.guessthenumber.models.Game;
import com.jobep.guessthenumber.models.Guess;
import com.jobep.guessthenumber.models.Round;
import com.jobep.guessthenumber.service.GuessTheNumberService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author powel
 */
@RestController
@RequestMapping("api/guessthenumber")
public class GuessTheNumberController {
    
    @Autowired
    GuessTheNumberService service;
    
    @PostMapping("begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int begin(){
        return service.createNewGame();
    }
    
    @PostMapping("guess")
    public Round guess(@RequestBody Guess guess){
        Round round = service.processGuess(guess.getGuess(), guess.getGameId());
        return round;
    }
    
    @GetMapping("game")
    public List<Game> game(){
        return service.getAllGames();
    }
    
    @GetMapping("game/{gameId}")
    public Game getGameById(@PathVariable int gameId){
        return service.getGameById(gameId);
    }
    
    @GetMapping("rounds/{gameId}")
    public List<Round> getRoundsById(@PathVariable int gameId){
        return service.getRoundsForGame(gameId);
    }
    
}
