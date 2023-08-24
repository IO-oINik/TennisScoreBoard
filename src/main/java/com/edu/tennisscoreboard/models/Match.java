package com.edu.tennisscoreboard.models;

import jakarta.persistence.*;

@Entity
@Table(name="Matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Player player1;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Player player2;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Player winner;

    protected Match() {}

    public Match(Player player1, Player player2, Player winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
