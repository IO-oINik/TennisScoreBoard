package com.edu.tennisscoreboard.controllers;

import com.edu.tennisscoreboard.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Iterator;

@Controller
public class MatchController {
    final private PlayersRepository playersRepository;
    final private MatchesRepository matchesRepository;
    final private MatchScoreRepository matchScoreRepository;

    @Autowired
    public MatchController(PlayersRepository playersRepository, MatchesRepository matchesRepository, MatchScoreRepository matchScoreRepository) {
        this.playersRepository = playersRepository;
        this.matchesRepository = matchesRepository;
        this.matchScoreRepository = matchScoreRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("matches", matchScoreRepository.getMatchScoreRep().values());
        return "index";
    }

    @GetMapping("/new-match")
    public String new_match(Model model) {
        model.addAttribute("match", new MatchForm());
        return "new_match";
    }

    @PostMapping("/new-match")
    public String create_new_match(@ModelAttribute MatchForm matchForm, Model model) {
        model.addAttribute("match", new MatchForm());
        // Првоерка валидности формы(заполнение полей)
        if(matchForm.getPlayerName1() == null || matchForm.getPlayerName2() == null) {
            model.addAttribute("error", "Поля не могут быть пустыми!");
            return "new_match";
        }
        // Если игроки с одинаковыми именами
        if(matchForm.getPlayerName1().equalsIgnoreCase(matchForm.getPlayerName2())) {
            model.addAttribute("error", "Одинаковые имена в полях недопустимы!");
            return "new_match";
        }

        Player player1 = playersRepository.findByNameIgnoreCase(matchForm.getPlayerName1());
        Player player2 = playersRepository.findByNameIgnoreCase(matchForm.getPlayerName2());
        // Нахождение Player  в БД или создание нового
        if(player1 == null) {
            player1 = playersRepository.save(new Player(matchForm.getPlayerName1()));
        }
        if(player2 == null) {
            player2 = playersRepository.save(new Player(matchForm.getPlayerName2()));
        }
        long uuid = Long.parseLong(Long.toString(player1.getId()) + "0" + Long.toString(player2.getId()));
        matchScoreRepository.getMatchScoreRep().put(uuid, new MatchScore(player1.getId(), player1, player2.getId(), player2, uuid));
        return "redirect:/match-score?uuid="+Long.toString(uuid);
    }

    @GetMapping("/match-score")
    public String match_score(@RequestParam long uuid, Model model) {
        MatchScore matchScore = matchScoreRepository.getMatchScoreRep().get(uuid);
        if(matchScore == null) {
            return "error/404";
        }
        model.addAttribute("match", matchScore);
        model.addAttribute("uuid", uuid);
        return "match-score";
    }

    @PostMapping("/match-score")
    public String match_score(@RequestParam long uuid, @RequestParam long id_winner, Model model) {
        MatchScore matchScore = matchScoreRepository.getMatchScoreRep().get(uuid);
        if (matchScore == null) {
            return "error/404";
        }
        model.addAttribute("match", matchScore);
        model.addAttribute("uuid", uuid);
        if(matchScore.addScorePlayerById(id_winner) == -1) {
            return "match-score";
        } else {
            Player player_winner;
            if(id_winner == matchScore.getPlayer1().getId())
                player_winner = matchScore.getPlayer1();
            else {
                player_winner = matchScore.getPlayer2();
            }
            Match match = new Match(matchScore.getPlayer1(), matchScore.getPlayer2(), player_winner);
            matchesRepository.save(match);
            model.addAttribute("player_winner", player_winner);
            matchScoreRepository.getMatchScoreRep().remove(uuid);
            return "match-win";
        }
    }

    @GetMapping("/matches")
    public String matches(@RequestParam(required = false) String filter_by_player_name, Model model) {
        Iterable<Match> matches;
        if(filter_by_player_name == null) {
            matches = matchesRepository.findAll();
        } else {
            Player player = playersRepository.findByNameIgnoreCase(filter_by_player_name);
            if(player == null) {
                matches = matchesRepository.findAll();
            } else {
                matches = matchesRepository.findAllByPlayer(player);
            }
        }
        model.addAttribute("matches", matches);
        return "matches";
    }
}
