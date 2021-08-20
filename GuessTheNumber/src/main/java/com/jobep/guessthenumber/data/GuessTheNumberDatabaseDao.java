/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.guessthenumber.data;

import com.jobep.guessthenumber.models.Game;
import com.jobep.guessthenumber.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author powel
 */
@Repository
public class GuessTheNumberDatabaseDao implements GuessTheNumberDao{
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public GuessTheNumberDatabaseDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public Round addRound(Round round) {
        final String sql = "INSERT INTO Round(guess,gameId,time,result) VALUES(?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                sql, 
                Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, round.getGuess());
            statement.setInt(2, round.getGameId());
            statement.setTimestamp(3,Timestamp.valueOf(round.getTime()));
            statement.setString(4,round.getResult());
            return statement;

        }, keyHolder);

        round.setId(keyHolder.getKey().intValue());

        return round;
    }

    @Override
    public Game addGame(Game game) {
        final String sql = "INSERT INTO Game(status, answer) VALUES (?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, game.getStatus());
            statement.setString(2,game.getAnswer());
            return statement;
            
        },keyHolder);
        
        game.setId(keyHolder.getKey().intValue());
        return game;
    }

    @Override
    public List<Round> getAllRounds() {
        final String sql = "SELECT  * FROM Round;";
        return jdbcTemplate.query(sql, new RoundMapper());
    }

    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT  * FROM Game;";
        return jdbcTemplate.query(sql, new GameMapper());
    }

    @Override
    public Round findRoundById(int id) {
        final String sql = "SELECT * FROM Round WHERE roundId = ?;";
        return jdbcTemplate.queryForObject(sql, new RoundMapper(), id);
    }

    @Override
    public Game findGameById(int id) {
        final String sql = "SELECT * FROM Game WHERE gameId = ?;";
        return jdbcTemplate.queryForObject(sql, new GameMapper(), id);
    }

    @Override
    public boolean updateRound(Round round) {
        final String sql = "UPDATE Round SET "
                + "gameId = ?, "
                + "guess = ?, "
                + "result = ?, "
                + "time = ? "
                + "WHERE roundId = ?;";

        return jdbcTemplate.update(sql,
                round.getGameId(),
                round.getGuess(),
                round.getResult(),
                Timestamp.valueOf(round.getTime()),
                round.getId()) > 0;
    }

    @Override
    public boolean updateGame(Game game) {
        final String sql = "UPDATE Game SET "
                + "status = ?,"
                + "answer = ? "
                + "WHERE gameId = ?;";
        return jdbcTemplate.update(sql,
                game.getStatus(),
                game.getAnswer(),
                game.getId()) > 0;
    }

    @Override
    public boolean deleteRoundById(int id) {
        final String sql = "DELETE FROM Round WHERE roundId = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public boolean deleteGameById(int id) {
        final String sqlFK = "DELETE FROM Round where gameId = ?";
        jdbcTemplate.update(sqlFK,id);
        final String sql = "DELETE FROM Game WHERE gameId = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public List<Round> getAllRoundsByTime() {
        final String sql = "SELECT  * FROM Round ORDER BY time;";
        return jdbcTemplate.query(sql, new RoundMapper());
    }
    
    private static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round td = new Round();
            td.setId(rs.getInt("roundID"));
            td.setGameId(rs.getInt("gameId"));
            td.setGuess(rs.getString("guess"));
            td.setTime(rs.getTimestamp("time").toLocalDateTime());
            td.setResult(rs.getString("result"));
            return td;
        }
    }
    
    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game td = new Game();
            td.setId(rs.getInt("gameId"));
            td.setStatus(rs.getString("status"));
            td.setAnswer(rs.getString("answer"));
            return td;
        }
    }
}
