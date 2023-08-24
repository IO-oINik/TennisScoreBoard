package com.edu.tennisscoreboard.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Scope("singleton")
public class MatchScoreRepository {
    final private HashMap<Long, MatchScore> matchScoreRep = new HashMap<>();

    public HashMap<Long, MatchScore> getMatchScoreRep() {
        return matchScoreRep;
    }
}
