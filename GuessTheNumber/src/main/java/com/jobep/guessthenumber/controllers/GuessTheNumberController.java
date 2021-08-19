/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.guessthenumber.controllers;

import com.jobep.guessthenumber.models.Game;
import com.jobep.guessthenumber.models.Round;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    @PostMapping("begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int begin(){
        return 0;
    }
    
    @PostMapping("guess")
    public Round guss(){
        return new Round();
    }
    
    @GetMapping("game")
    public List<Game> game(){
        return new ArrayList<Game>();
    }
    
    @GetMapping("game/{gameId}")
    public Game getGameById(){
        return new Game();
    }
    
    @GetMapping("rounds/{gameId}")
    public List<Round> getRoundsById(){
        return new ArrayList<Round>();
    }
    
}
