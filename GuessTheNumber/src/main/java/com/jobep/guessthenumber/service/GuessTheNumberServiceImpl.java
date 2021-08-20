/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.guessthenumber.service;

import com.jobep.guessthenumber.data.GuessTheNumberDatabaseDao;
import com.jobep.guessthenumber.models.Game;
import com.jobep.guessthenumber.models.Round;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author powel
 */
@Component
public class GuessTheNumberServiceImpl implements GuessTheNumberService{
    
    @Autowired
    private GuessTheNumberDatabaseDao dao;

    @Override
    public int createNewGame() {
        Random rand = new Random();
        int newDigit;
        String answer = "";
        Game game = new Game();
        game.setStatus("In-Progress");
        while(answer.length() < 4){
            newDigit = rand.nextInt(9)+1;
            if(answer.contains(newDigit + ""))
                continue;
            else
                answer += newDigit + "";
        }
        game.setAnswer(answer);
        dao.addGame(game);
        return game.getId();
        
    }

    @Override
    public Round processGuess(String guess, int gameId) {
        Round round = new Round();
        round.setGuess(guess);
        Game game = getGame(gameId);
        round.setGameId(game.getId());
        if(game!=null){
            String answer = game.getAnswer();
            int exact=0;
            int partial=0;
            for(int i = 0; i < 4; i++){
                if(guess.charAt(i) == answer.charAt(i))
                    exact++;
                else if(answer.contains(guess.charAt(i) +""))
                    partial++;
            }
            String result = String.format("e:%dp:%d",exact,partial);
            round.setResult(result);
            if(exact == 4)
                game.setStatus("Finished");
            dao.updateGame(game);
            dao.addRound(round);
            return round;
        }
        return null;
    }

    @Override
    public List<Game> getAllGames() {
        List<Game> games = dao.getAllGames();
        for(Game game : games){
            if(game.getStatus().equals("In-Progress"))
                game.setAnswer("");
        }
        return games;
    }
    
    @Override
    public Game getGameById(int gameId){
        Game game = getGame(gameId);
        if(game.getStatus().equals("In-Progress"))
            game.setAnswer("");
        return game;
        
    }
    
    
    public Game getGame(int gameId) {
        List<Game> games = dao.getAllGames();
        List<Game> gameToRet = (games.stream().filter(g -> g.getId() == gameId).collect(Collectors.toList()));
        if(gameToRet.isEmpty())
            return null;
        return gameToRet.get(0);
    }

    @Override
    public List<Round> getRoundsForGame(int gameId) {
        List<Round> rounds = dao.getAllRoundsByTime();
        rounds = rounds.stream().filter( r -> r.getGameId() == gameId).collect(Collectors.toList());
        return rounds;
    }
    @Override
    public boolean clearDB(){
        List<Game> games = dao.getAllGames();
        for(Game game : games)
            dao.deleteGameById(game.getId());
        List<Round> rounds = dao.getAllRounds();
        for(Round round : rounds)
            dao.deleteRoundById(round.getId());
        games = dao.getAllGames();
        rounds = dao.getAllRounds();
        return games.isEmpty() && rounds.isEmpty();
    }
    
    @Override
    public int addGame(Game game){
        return dao.addGame(game).getId();
    }
}
