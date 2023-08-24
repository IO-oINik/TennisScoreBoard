package com.edu.tennisscoreboard.models;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MatchScore {
    @EqualsAndHashCode.Include
    private final long IdPlayer1;
    @EqualsAndHashCode.Include
    private final long IdPlayer2;
    @EqualsAndHashCode.Include
    private final long uuid;

    private final Player player1;
    private final Player player2;
    private int ScorePlayer1;
    private int ScorePlayer2;
    private int winGamePlayer1;
    private int winGamePlayer2;
    private int winSetPlayer1;
    private int winSetPlayer2;

    public MatchScore(long idPlayer1, Player player1, long idPlayer2, Player player2, long uuid) {
        IdPlayer1 = idPlayer1;
        IdPlayer2 = idPlayer2;
        this.player1 = player1;
        this.player2 = player2;
        this.uuid = uuid;
        ScorePlayer1 = 0;
        ScorePlayer2 = 0;
        winGamePlayer1 = 0;
        winGamePlayer2 = 0;
        winSetPlayer1 = 0;
        winSetPlayer2 = 0;
    }

    public long addScorePlayerById(long id) {

        // Тай-брейк
        if(winGamePlayer1 == 6 && winGamePlayer2 == 6) {
            if(id == IdPlayer1)
                ScorePlayer1++;
            else if(id == IdPlayer2)
                ScorePlayer2++;
            if(Math.abs(ScorePlayer1 - ScorePlayer2) == 2 && ScorePlayer1 >= 7) {
                winGamePlayer1++;
                ScorePlayer1 = 0;
                ScorePlayer2 = 0;
            } else if(Math.abs(ScorePlayer1 - ScorePlayer2) == 2 && ScorePlayer2 >= 7) {
                winGamePlayer2++;
                ScorePlayer1 = 0;
                ScorePlayer2 = 0;
            } else {
                return -1;
            }
        } else {
            // Добавление очка одному из игроков
            if (id == IdPlayer1) {
                ScorePlayer1 = addScore(ScorePlayer1);
                if(ScorePlayer1 == 101 && ScorePlayer2 == 40)
                    ScorePlayer2 = 100;
            } else if (id == IdPlayer2) {
                ScorePlayer2 = addScore(ScorePlayer2);
                if(ScorePlayer2 == 101 && ScorePlayer1 == 40)
                    ScorePlayer1 = 100;
            }
        }

        // Выйгрыш гейма
        if (ScorePlayer1 == 50 || ScorePlayer1 == 7) {
            winGamePlayer1++;
            ScorePlayer1 = 0;
            ScorePlayer2 = 0;
        } else if (ScorePlayer2 == 50 || ScorePlayer2 == 7) {
            winGamePlayer2++;
            ScorePlayer1 = 0;
            ScorePlayer2 = 0;
        } else if(Math.abs(ScorePlayer1 - ScorePlayer2) == 2) {
            if(ScorePlayer1 > ScorePlayer2) {
                winGamePlayer1++;
                ScorePlayer1 = 0;
                ScorePlayer2 = 0;
            } else {
                winGamePlayer2++;
                ScorePlayer1 = 0;
                ScorePlayer2 = 0;
            }
        }

        // Выйгрыш сета
        if(Math.abs(winGamePlayer1 - winGamePlayer2) > 2) {
            if (winGamePlayer1 == 6) {
                winSetPlayer1++;
                winGamePlayer1 = 0;
                winGamePlayer2 = 0;
            } else if (winGamePlayer2 == 6) {
                winSetPlayer2++;
                winGamePlayer1 = 0;
                winGamePlayer2 = 0;
            }
        } else {
            if (winGamePlayer1 == 7) {
                winSetPlayer1++;
                winGamePlayer1 = 0;
                winGamePlayer2 = 0;
            } else if (winGamePlayer2 == 7) {
                winSetPlayer2++;
                winGamePlayer1 = 0;
                winGamePlayer2 = 0;
            }
        }

        // Выйгрыш матча
        if(winSetPlayer1 == 2) {
            return IdPlayer1;
        }else if (winSetPlayer2 == 2) {
            return IdPlayer2;
        }
        return -1;
    }

    private int addScore(int score) {
        if (score == 0)
            return 15;
        if (score==15)
            return 30;
        if (score==30)
            return 40;
        if(ScorePlayer1==40 && ScorePlayer2==40) {
            return 101;
        }
        if(score >= 100)
            return ++score;
        return 50;
    }

    public long getUuid() {
        return uuid;
    }

    public long getIdPlayer1() {
        return IdPlayer1;
    }

    public long getIdPlayer2() {
        return IdPlayer2;
    }

    public int getScorePlayer1() {
        return ScorePlayer1;
    }

    public void setScorePlayer1(int scorePlayer1) {
        ScorePlayer1 = scorePlayer1;
    }

    public int getScorePlayer2() {
        return ScorePlayer2;
    }

    public void setScorePlayer2(int scorePlayer2) {
        ScorePlayer2 = scorePlayer2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getWinGamePlayer1() {
        return winGamePlayer1;
    }

    public int getWinGamePlayer2() {
        return winGamePlayer2;
    }

    public int getWinSetPlayer1() {
        return winSetPlayer1;
    }

    public int getWinSetPlayer2() {
        return winSetPlayer2;
    }

}
