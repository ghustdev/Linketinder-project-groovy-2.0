package controllers

import entities.Match
import services.CurtidaService

class MatchController {
    private final CurtidaService curtidaService

    MatchController(CurtidaService curtidaService) {
        this.curtidaService = curtidaService
    }

    List<Match> listarTodosMatches() {
        return curtidaService.obterTodosMatches()
    }
}

