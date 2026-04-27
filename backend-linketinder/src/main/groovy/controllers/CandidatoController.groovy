package controllers

import entities.Candidato
import entities.Curtida
import entities.Match
import services.CandidatoService
import services.CurtidaService

class CandidatoController {
    private final CandidatoService candidatoService
    private final CurtidaService curtidaService

    CandidatoController(CandidatoService candidatoService, CurtidaService curtidaService) {
        this.candidatoService = candidatoService
        this.curtidaService = curtidaService
    }

    Candidato criarCandidato(Candidato candidato) {
        return candidatoService.criarCandidato(candidato)
    }

    List<Candidato> listarCandidatos() {
        return candidatoService.listarCandidatos()
    }

    Candidato buscarCandidatoPorCpf(String cpf) {
        return candidatoService.buscarCandidato(cpf)
    }

    List<Curtida> listarCurtidasDoCandidato(Candidato candidato) {
        return curtidaService.listarCurtidasCandidato(candidato)
    }

    List<Match> listarMatchesDoCandidato(Candidato candidato) {
        return curtidaService.obterTodosMatches().findAll { it.candidato?.cpf == candidato?.cpf }
    }
}
